package com.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Gender
import com.healthtracker.model.Goal
import java.time.LocalDate

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val fullName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val weightKg: Double,
    val heightCm: Double,
    val activityLevel: ActivityLevel,
    val goal: Goal

)
