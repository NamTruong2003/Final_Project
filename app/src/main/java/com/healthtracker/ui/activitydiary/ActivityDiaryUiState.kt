package com.healthtracker.ui.activitydiary

import com.healthtracker.model.ActivityType
import com.healthtracker.model.ActivityWithTypeInfo
import java.time.LocalDate

data class ActivityDiaryUiState(
    val isLoading: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val activities: List<ActivityWithTypeInfo> = emptyList(),
    val activityTypes: List<ActivityType> = emptyList(),

    val isAddSheetOpen: Boolean = false,
    val selectedActivityType: ActivityType? = null,
    val durationMinutes: Int = 30,
    val isSaving: Boolean = false
) {
    val totalCaloriesBurned: Int
        get() = activities.sumOf { it.caloriesBurned }

    val estimatedCaloriesForSelection: Int
        get() {
            val type = selectedActivityType ?: return 0
            val hours = durationMinutes / 60.0
            return (type.metValue * 60.0 * hours).toInt()
        }
}