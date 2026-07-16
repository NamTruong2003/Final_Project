package com.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.healthtracker.model.MealType
import java.time.LocalDate

@Entity(tableName = "meal_entry")
data class MealEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foodId:Int,
    val quantity:Int,
    val mealType: MealType,
    val date: LocalDate,
    val totalCalories: Int
)
