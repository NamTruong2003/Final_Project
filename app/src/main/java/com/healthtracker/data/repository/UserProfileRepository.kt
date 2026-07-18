package com.healthtracker.data.repository

import com.healthtracker.data.local.dao.UserProfileDao
import com.healthtracker.data.local.entity.UserProfileEntity
import com.healthtracker.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepository @Inject constructor(
    private val userProfileDao: UserProfileDao
){
    suspend fun getProfile(): UserProfile? {
        return userProfileDao.getProfile()?.toModel()
    }
    fun observeProfile(): Flow<UserProfile?>{
        return userProfileDao.observeProfile().map { it?.toModel() as UserProfile }
    }
    suspend fun saveProfile(profile: UserProfile) {
        userProfileDao.insertOrUpdate(profile.toEntity())
    }
    suspend fun deleteProfile() {
        userProfileDao.deleteProfile()
    }

    suspend fun hasProfile(): Boolean {
        return userProfileDao.getProfile() != null
    }

}
private fun UserProfileEntity.toModel() = UserProfile(
    fullName = fullName,
    dateOfBirth = dateOfBirth,
    gender = gender,
    heightCm = heightCm,
    weightKg = weightKg,
    activityLevel = activityLevel,
    goal = goal
)
private fun UserProfile.toEntity() = UserProfileEntity(
    id = 1,
    fullName = fullName,
    dateOfBirth = dateOfBirth,
    gender = gender,
    heightCm = heightCm,
    weightKg = weightKg,
    activityLevel = activityLevel,
    goal = goal
)