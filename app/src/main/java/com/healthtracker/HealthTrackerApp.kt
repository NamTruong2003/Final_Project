package com.healthtracker

import android.app.Application
import com.healthtracker.data.repository.ActivityTypeRepository
import com.healthtracker.data.repository.FoodRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class HealthTrackerApp : Application() {

    @Inject lateinit var foodRepository: FoodRepository
    @Inject lateinit var activityTypeRepository: ActivityTypeRepository

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            foodRepository.seedIfEmpty()
            activityTypeRepository.seedIfEmpty()
        }
    }
}