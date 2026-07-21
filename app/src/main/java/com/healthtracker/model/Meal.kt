package com.healthtracker.model

import java.time.LocalDate

data class  Meal(
    val id: Int = 0,
    val foodId: Int?,
    val customFoodName: String?,
    val quantity: Int,
    val mealType: MealType,
    val date: LocalDate,
    val totalCalories: Int
){
    val isCustomFood: Boolean get() = foodId == null
}