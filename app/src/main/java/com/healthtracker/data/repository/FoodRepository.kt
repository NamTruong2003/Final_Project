package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.FoodDao
import com.healthtracker.data.local.entity.FoodEntity
import com.healthtracker.model.Food
import com.healthtracker.util.FoodSeedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodDao: FoodDao
) {
    fun observeAllFoods(): Flow<List<Food>> {
        return foodDao.observeAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    fun searchFood(keyword: String): Flow<List<Food>> {
        return foodDao.searchByName(keyword).map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun getFoodById(id: Int): Food? {
        return foodDao.getById(id)?.toModel()
    }

    suspend fun seedIfEmpty() {
        if (foodDao.getCount() == 0) {
            foodDao.insertAll(FoodSeedData.foods)
        }
    }
}

private fun FoodEntity.toModel() = Food(
    id = id,
    name = name,
    caloriesPerUnit = caloriesPerUnit,
    unitType = unitType,
    unitLabel = unitLabel,
    imageResName = imageResName
)