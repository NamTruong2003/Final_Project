package com.healthtracker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.ActivityRepository
import com.healthtracker.data.repository.MealRepository
import com.healthtracker.data.repository.UserProfileRepository
import com.healthtracker.util.TdeeCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard(LocalDate.now())
    }

    fun loadDashboard(date: LocalDate) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, date = date) }

            val profile = userProfileRepository.getProfile()
            val caloriesIn = mealRepository.getTotalCaloriesByDate(date)
            val caloriesOut = activityRepository.getTotalCaloriesByDate(date)
            val goal = profile?.let { TdeeCalculator.calculate(it) } ?: 0

            _uiState.update {
                it.copy(
                    isLoading = false,
                    userName = profile?.fullName ?: "",
                    goalCalories = goal,
                    caloriesIn = caloriesIn,
                    caloriesOut = caloriesOut
                )
            }
        }
    }
}