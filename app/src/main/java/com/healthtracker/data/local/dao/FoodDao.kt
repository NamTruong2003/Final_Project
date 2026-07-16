package com.healthtracker.data.local.dao

import androidx.room.*
import com.healthtracker.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY nameVi ASC")
    fun observeAll(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE nameVi LIKE '%' || :keyword || '%'")
    fun searchByName(keyword: String): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id = :id")
    suspend fun getById(id: Int): FoodEntity?

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Insert
    suspend fun insert(food: FoodEntity): Long
}