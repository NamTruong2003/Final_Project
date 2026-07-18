package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.ActivityDao
import com.healthtracker.data.local.entity.ActivityEntryEntity
import com.healthtracker.model.Activity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ActivityRepository @Inject constructor(
    private val activityDao: ActivityDao,
    private val activityTypeRepository: ActivityTypeRepository,
    private val userProfileRepository: UserProfileRepository
) {
    fun observeActivitiesByDate(date: LocalDate): Flow<List<Activity>> {
        return activityDao.observeByDate(date).map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun getTotalCaloriesByDate(date: LocalDate): Int {
        return activityDao.getTotalCaloriesByDate(date) ?: 0
    }


    suspend fun addActivityEntry(activityTypeId: Int, durationMinutes: Int, date: LocalDate) {
        val activityType = activityTypeRepository.getActivityTypeById(activityTypeId) ?: return
        val profile = userProfileRepository.getProfile() ?: return

        val hours = durationMinutes / 60.0
        val caloriesBurned = (activityType.metValue * profile.weightKg * hours).toInt()

        val entry = ActivityEntryEntity(
            activityTypeId = activityTypeId,
            durationMinutes = durationMinutes,
            date = date,
            caloriesBurned = caloriesBurned
        )
        activityDao.insert(entry)
    }

    suspend fun deleteActivityEntry(activity: Activity) {
        activityDao.delete(activity.toEntity())
    }
}

private fun ActivityEntryEntity.toModel() = Activity(
    id = id,
    activityTypeId = activityTypeId,
    durationMinutes = durationMinutes,
    date = date,
    caloriesBurned = caloriesBurned
)

private fun Activity.toEntity() = ActivityEntryEntity(
    id = id,
    activityTypeId = activityTypeId,
    durationMinutes = durationMinutes,
    date = date,
    caloriesBurned = caloriesBurned
)