package com.healthtracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "activity_entry")
data class ActivityEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val activityTypeId: Int,
    val durationMinutes: Int,
    val date: LocalDate,
    val caloriesBurned: Int
)