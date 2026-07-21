package com.healthtracker.model

import java.time.LocalDate

data class MealWithFoodInfo(
    val id: Int,
    val displayName: String,
    val quantity: Int,
    val unitLabel: String,
    val mealType: MealType,
    val date: LocalDate,
    val totalCalories: Int
)