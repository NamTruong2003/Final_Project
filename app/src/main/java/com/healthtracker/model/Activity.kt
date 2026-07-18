package com.healthtracker.model

import java.time.LocalDate

data class Activity(
    val id: Int = 0,
    val activityTypeId: Int,
    val durationMinutes: Int,
    val date: LocalDate,
    val caloriesBurned: Int
)