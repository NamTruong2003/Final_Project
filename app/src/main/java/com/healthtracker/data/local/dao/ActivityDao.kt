package com.healthtracker.data.local.dao

import androidx.room.*
import com.healthtracker.data.local.entity.ActivityEntryEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activity_entry WHERE date = :date")
    fun observeByDate(date: LocalDate): Flow<List<ActivityEntryEntity>>

    @Query("SELECT SUM(caloriesBurned) FROM activity_entry WHERE date = :date")
    suspend fun getTotalCaloriesByDate(date: LocalDate): Int?

    @Insert
    suspend fun insert(entry: ActivityEntryEntity)

    @Delete
    suspend fun delete(entry: ActivityEntryEntity)
}