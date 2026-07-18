package com.healthtracker.model

import java.time.LocalDate

data class Meal(
    val id: Int = 0,
    val foodId: Int,
    val quantity: Int,
    val mealType: MealType,
    val date: LocalDate,
    val totalCalories: Int
)