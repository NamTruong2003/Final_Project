package com.healthtracker.model

import java.time.LocalDate

data class ActivityWithTypeInfo(
    val id: Int,
    val activityTypeName: String,
    val iconName: String,
    val durationMinutes: Int,
    val caloriesBurned: Int,
    val date: LocalDate
)