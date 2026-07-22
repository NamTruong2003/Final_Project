package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.ActivityTypeDao
import com.healthtracker.data.local.entity.ActivityTypeEntity
import com.healthtracker.model.ActivityType
import com.healthtracker.util.ActivityTypeSeedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivityTypeRepository @Inject constructor(
    private val activityTypeDao: ActivityTypeDao
) {
    fun observeAllActivityTypes(): Flow<List<ActivityType>> {
        return activityTypeDao.observeAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun getActivityTypeById(id: Int): ActivityType? {
        return activityTypeDao.getById(id)?.toModel()
    }

    suspend fun seedIfEmpty() {
        if (activityTypeDao.getCount() == 0) {
            activityTypeDao.insertAll(ActivityTypeSeedData.activityTypes)
        }
    }
}

private fun ActivityTypeEntity.toModel() = ActivityType(
    id = id,
    name = name,
    metValue = metValue,
    iconName = iconName
)