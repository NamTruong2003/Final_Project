package com.healthtracker.model

import com.healthtracker.data.local.entity.FoodUnitType

data class Food(
    val id: Int = 0,
    val name: String,
    val caloriesPerUnit: Int,
    val unitType: FoodUnitType,
    val unitLabel: String,
    val imageResName: String? = null
)
