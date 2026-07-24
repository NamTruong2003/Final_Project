package com.healthtracker.di

import android.content.Context
import androidx.room.Room
import com.healthtracker.data.local.AppDatabase
import com.healthtracker.data.local.dao.ActivityDao
import com.healthtracker.data.local.dao.ActivityTypeDao
import com.healthtracker.data.local.dao.FoodDao
import com.healthtracker.data.local.dao.MealDao
import com.healthtracker.data.local.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "health_tracker.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideUserProfileDao(db: AppDatabase): UserProfileDao = db.userProfileDao()

    @Provides
    fun provideFoodDao(db: AppDatabase): FoodDao = db.foodDao()

    @Provides
    fun provideMealDao(db: AppDatabase): MealDao = db.mealDao()

    @Provides
    fun provideActivityTypeDao(db: AppDatabase): ActivityTypeDao = db.activityTypeDao()

    @Provides
    fun provideActivityDao(db: AppDatabase): ActivityDao = db.activityDao()
}