package com.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val caloriesPerUnit: Int,
    val unitType: FoodUnitType,
    val unitLabel: String,
    val imageResName: String? = null
)
enum class FoodUnitType {
    PER_100G,
    PER_SERVING
}
