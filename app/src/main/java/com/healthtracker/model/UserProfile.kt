package com.healthtracker.model

import java.time.LocalDate
import java.time.Period

data class UserProfile(
    val fullName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    val weightKg: Double,
    val heightCm: Double,
    val activityLevel: ActivityLevel,
    val goal: Goal
) {
    val age: Int
        get() = Period.between(dateOfBirth, LocalDate.now()).years
}