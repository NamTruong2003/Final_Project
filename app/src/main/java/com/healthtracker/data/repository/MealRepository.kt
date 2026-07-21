package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.MealDao
import com.healthtracker.data.local.entity.FoodUnitType
import com.healthtracker.data.local.entity.MealEntryEntity
import com.healthtracker.model.Meal
import com.healthtracker.model.MealType
import com.healthtracker.model.MealWithFoodInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealDao: MealDao,
    private val foodRepository: FoodRepository
) {
    fun observeMealsByDate(date: LocalDate): Flow<List<MealWithFoodInfo>> {
        return mealDao.observeByDateWithFoodInfo(date).map { rows ->
            rows.map {
                MealWithFoodInfo(
                    id = it.id,
                    displayName = it.displayName,
                    quantity = it.quantity,
                    unitLabel = it.unitLabel,
                    mealType = it.mealType,
                    date = it.date,
                    totalCalories = it.totalCalories
                )
            }
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
    suspend fun addMealFromFood(foodId: Int, quantity: Int, mealType: MealType, date: LocalDate) {
        val food = foodRepository.getFoodById(foodId) ?: return

        val totalCalories = when (food.unitType) {
            FoodUnitType.PER_100G -> (food.caloriesPerUnit * quantity) / 100
            FoodUnitType.PER_SERVING -> food.caloriesPerUnit * quantity
        }

        val entry = MealEntryEntity(
            foodId = foodId,
            customFoodName = null,
            quantity = quantity,
            mealType = mealType,
            date = date,
            totalCalories = totalCalories
        )
        mealDao.insert(entry)
    }
    suspend fun deleteMeal(id: Int) {
        mealDao.deleteById(id)
    }
    suspend fun addCustomMeal(
        customFoodName: String,
        totalCalories: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        val entry = MealEntryEntity(
            foodId = null,
            customFoodName = customFoodName,
            quantity = 1,
            mealType = mealType,
            date = date,
            totalCalories = totalCalories
        )
        mealDao.insert(entry)
    }
}

private fun MealEntryEntity.toModel() = Meal(
    id = id,
    foodId = foodId,
    customFoodName = customFoodName,
    quantity = quantity,
    mealType = mealType,
    date = date,
    totalCalories = totalCalories
)

private fun Meal.toEntity() = MealEntryEntity(
    id = id,
    foodId = foodId,
    customFoodName = customFoodName,
    quantity = quantity,
    mealType = mealType,
    date = date,
    totalCalories = totalCalories
)