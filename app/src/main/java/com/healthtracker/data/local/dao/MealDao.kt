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

    @Query("DELETE FROM meal_entry WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("""
        SELECT 
            m.id AS id,
            COALESCE(f.name, m.customFoodName) AS displayName,
            m.quantity AS quantity,
            COALESCE(f.unitLabel, '') AS unitLabel,
            m.mealType AS mealType,
            m.date AS date,
            m.totalCalories AS totalCalories
        FROM meal_entry m
        LEFT JOIN foods f ON m.foodId = f.id
        WHERE m.date = :date
        ORDER BY m.mealType
    """)
    fun observeByDateWithFoodInfo(date: LocalDate): Flow<List<MealWithFoodInfoRow>>
}



data class MealWithFoodInfoRow(
    val id: Int,
    val displayName: String,
    val quantity: Int,
    val unitLabel: String,
    val mealType: MealType,
    val date: LocalDate,
    val totalCalories: Int
)