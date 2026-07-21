package com.healthtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.healthtracker.data.local.dao.ActivityDao
import com.healthtracker.data.local.dao.ActivityTypeDao
import com.healthtracker.data.local.dao.FoodDao
import com.healthtracker.data.local.dao.MealDao
import com.healthtracker.data.local.dao.UserProfileDao
import com.healthtracker.data.local.entity.ActivityEntryEntity
import com.healthtracker.data.local.entity.ActivityTypeEntity
import com.healthtracker.data.local.entity.FoodEntity
import com.healthtracker.data.local.entity.MealEntryEntity
import com.healthtracker.data.local.entity.UserProfileEntity

@Database(
    entities = [
        UserProfileEntity::class,
        FoodEntity::class,
        MealEntryEntity::class,
        ActivityTypeEntity::class,
        ActivityEntryEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun foodDao(): FoodDao
    abstract fun mealDao(): MealDao
    abstract fun activityTypeDao(): ActivityTypeDao
    abstract fun activityDao(): ActivityDao
}