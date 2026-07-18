package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.MealDao
import com.healthtracker.data.local.entity.MealEntryEntity
import com.healthtracker.model.Meal
import com.healthtracker.model.MealType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealDao: MealDao
) {
    fun observeMealsByDate(date: LocalDate): Flow<List<Meal>> {
        return mealDao.observeByDate(date).map { entities ->
            entities.map { it.toModel() }
        }
    }

    fun observeMealsByDateAndType(date: LocalDate, mealType: MealType): Flow<List<Meal>> {
        return mealDao.observeByDateAndType(date, mealType).map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun getTotalCaloriesByDate(date: LocalDate): Int {
        return mealDao.getTotalCaloriesByDate(date) ?: 0
    }

    suspend fun addMeal(meal: Meal) {
        mealDao.insert(meal.toEntity())
    }

    suspend fun deleteMeal(meal: Meal) {
        mealDao.delete(meal.toEntity())
    }
}

private fun MealEntryEntity.toModel() = Meal(
    id = id,
    foodId = foodId,
    quantity = quantity,
    mealType = mealType,
    date = date,
    totalCalories = totalCalories
)

private fun Meal.toEntity() = MealEntryEntity(
    id = id,
    foodId = foodId,
    quantity = quantity,
    mealType = mealType,
    date = date,
    totalCalories = totalCalories
)