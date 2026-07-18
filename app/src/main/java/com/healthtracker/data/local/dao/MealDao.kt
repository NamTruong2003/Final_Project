package com.healthtracker.data.local.dao

import androidx.room.*
import com.healthtracker.data.local.entity.MealEntryEntity
import com.healthtracker.model.MealType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MealDao {

    @Query("SELECT * FROM meal_entry WHERE date = :date")
    fun observeByDate(date: LocalDate): Flow<List<MealEntryEntity>>

    @Query("SELECT * FROM meal_entry WHERE date = :date AND mealType = :mealType")
    fun observeByDateAndType(date: LocalDate, mealType: MealType): Flow<List<MealEntryEntity>>

    @Query("SELECT SUM(totalCalories) FROM meal_entry WHERE date = :date")
    suspend fun getTotalCaloriesByDate(date: LocalDate): Int?

    @Insert
    suspend fun insert(entry: MealEntryEntity)

    @Delete
    suspend fun delete(entry: MealEntryEntity)
}