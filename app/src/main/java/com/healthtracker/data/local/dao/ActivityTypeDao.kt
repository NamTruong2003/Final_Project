package com.healthtracker.data.local.dao

import androidx.room.*
import com.healthtracker.data.local.entity.ActivityTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityTypeDao {

    @Query("SELECT * FROM activity_types")
    fun observeAll(): Flow<List<ActivityTypeEntity>>

    @Query("SELECT * FROM activity_types WHERE id = :id")
    suspend fun getById(id: Int): ActivityTypeEntity?

    @Query("SELECT COUNT(*) FROM activity_types")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(activityTypes: List<ActivityTypeEntity>)
}