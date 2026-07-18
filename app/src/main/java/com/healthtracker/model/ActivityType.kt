package com.healthtracker.model

data class ActivityType(
    val id: Int = 0,
    val name: String,
    val metValue: Double,
    val iconName: String? = ""
)