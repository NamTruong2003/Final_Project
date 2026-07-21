package com.healthtracker.ui.mealdiary

import com.healthtracker.model.Food
import com.healthtracker.model.MealType
import com.healthtracker.model.MealWithFoodInfo
import java.time.LocalDate

data class MealDiaryUiState(
    val isLoading: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val mealsByType: Map<MealType, List<MealWithFoodInfo>> = emptyMap(),
    val goalCalories: Int = 0,

    val isAddSheetOpen: Boolean = false,
    val addSheetMealType: MealType = MealType.BREAKFAST,
    val isCustomTabSelected: Boolean = false,

    val foodSearchQuery: String = "",
    val foodSearchResults: List<Food> = emptyList(),
    val selectedFood: Food? = null,
    val quantityInput: String = "",

    val customFoodName: String = "",
    val customFoodCalories: String = "",

    val isSaving: Boolean = false
) {
    val totalCalories: Int
        get() = mealsByType.values.flatten().sumOf { it.totalCalories }

    fun caloriesForType(type: MealType): Int =
        mealsByType[type]?.sumOf { it.totalCalories } ?: 0

    val progressFraction: Float
        get() = if (goalCalories == 0) 0f else (totalCalories.toFloat() / goalCalories).coerceIn(0f, 1f)
}