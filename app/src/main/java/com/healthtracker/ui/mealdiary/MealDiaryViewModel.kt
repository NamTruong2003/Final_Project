package com.healthtracker.ui.mealdiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.FoodRepository
import com.healthtracker.data.repository.MealRepository
import com.healthtracker.data.repository.UserProfileRepository
import com.healthtracker.model.MealType
import com.healthtracker.model.MealWithFoodInfo
import com.healthtracker.util.TdeeCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MealDiaryViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val foodRepository: FoodRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealDiaryUiState())
    val uiState: StateFlow<MealDiaryUiState> = _uiState.asStateFlow()

    private var mealsJob: Job? = null
    private var searchJob: Job? = null

    init {
        loadDate(LocalDate.now())
        loadGoalCalories()
    }

    fun loadDate(date: LocalDate) {
        _uiState.update { it.copy(date = date, isLoading = true) }
        mealsJob?.cancel()
        mealsJob = mealRepository.observeMealsByDate(date)
            .onEach { meals ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mealsByType = meals.groupBy { m -> m.mealType }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadGoalCalories() {
        viewModelScope.launch {
            val profile = userProfileRepository.getProfile()
            val goal = profile?.let { TdeeCalculator.calculate(it) } ?: 0
            _uiState.update { it.copy(goalCalories = goal) }
        }
    }

    fun onPreviousDay() = loadDate(_uiState.value.date.minusDays(1))
    fun onNextDay() = loadDate(_uiState.value.date.plusDays(1))

    // ===== Add Food Sheet =====

    fun openAddSheet(mealType: MealType) {
        _uiState.update {
            it.copy(
                isAddSheetOpen = true,
                addSheetMealType = mealType,
                isCustomTabSelected = false,
                foodSearchQuery = "",
                foodSearchResults = emptyList(),
                selectedFood = null,
                quantityInput = "",
                customFoodName = "",
                customFoodCalories = ""
            )
        }
        onSearchQueryChange("")
    }

    fun closeAddSheet() {
        _uiState.update { it.copy(isAddSheetOpen = false) }
    }

    fun onSelectTab(isCustom: Boolean) {
        _uiState.update { it.copy(isCustomTabSelected = isCustom) }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(foodSearchQuery = query, selectedFood = null) }
        searchJob?.cancel()
        searchJob = if (query.isBlank()) {
            foodRepository.observeAllFoods()
                .onEach { results -> _uiState.update { it.copy(foodSearchResults = results) } }
                .launchIn(viewModelScope)
        } else {
            foodRepository.searchFood(query)
                .onEach { results -> _uiState.update { it.copy(foodSearchResults = results) } }
                .launchIn(viewModelScope)
        }
    }

    fun onSelectFood(food: com.healthtracker.model.Food) {
        _uiState.update { it.copy(selectedFood = food, quantityInput = "1") }
    }

    fun onQuantityChange(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,4}$"))) {
            _uiState.update { it.copy(quantityInput = value) }
        }
    }

    fun onCustomNameChange(name: String) {
        _uiState.update { it.copy(customFoodName = name) }
    }

    fun onCustomCaloriesChange(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,5}$"))) {
            _uiState.update { it.copy(customFoodCalories = value) }
        }
    }

    fun onConfirmAdd() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            if (state.isCustomTabSelected) {
                val calories = state.customFoodCalories.toIntOrNull()
                if (state.customFoodName.isNotBlank() && calories != null && calories > 0) {
                    mealRepository.addCustomMeal(
                        customFoodName = state.customFoodName,
                        totalCalories = calories,
                        mealType = state.addSheetMealType,
                        date = state.date
                    )
                }
            } else {
                val food = state.selectedFood
                val quantity = state.quantityInput.toIntOrNull()
                if (food != null && quantity != null && quantity > 0) {
                    mealRepository.addMealFromFood(
                        foodId = food.id,
                        quantity = quantity,
                        mealType = state.addSheetMealType,
                        date = state.date
                    )
                }
            }

            _uiState.update { it.copy(isSaving = false, isAddSheetOpen = false) }
        }
    }

    fun onDeleteMeal(mealId: Int) {
        viewModelScope.launch {
            mealRepository.deleteMeal(mealId)
        }
    }
}