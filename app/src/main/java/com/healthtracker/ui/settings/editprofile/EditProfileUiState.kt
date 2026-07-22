package com.healthtracker.ui.settings.editprofile

import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Gender
import com.healthtracker.model.Goal
import com.healthtracker.model.UserProfile
import com.healthtracker.util.BmiCalculator
import com.healthtracker.util.TdeeCalculator
import java.time.LocalDate

data class EditProfileUiState(
    val name: String = "",
    val dateOfBirth: LocalDate? = null,
    val gender: Gender? = null,
    val weightKg: String = "",
    val heightCm: String = "",
    val activityLevel: ActivityLevel? = null,
    val goal: Goal? = null,

    val nameError: Int? = null,
    val dateOfBirthError: Int? = null,
    val weightError: Int? = null,
    val heightError: Int? = null,

    val isLoading: Boolean = true,
    val isSaving: Boolean = false
) {
    val previewBmi: Double?
        get() {
            val w = weightKg.toDoubleOrNull() ?: return null
            val h = heightCm.toDoubleOrNull() ?: return null
            if (h <= 0) return null
            return BmiCalculator.calculate(w, h)
        }

    val previewTdee: Int?
        get() {
            val dob = dateOfBirth ?: return null
            val g = gender ?: return null
            val w = weightKg.toDoubleOrNull() ?: return null
            val h = heightCm.toDoubleOrNull() ?: return null
            val level = activityLevel ?: return null
            val goalValue = goal ?: return null

            val tempProfile = UserProfile(
                fullName = name,
                dateOfBirth = dob,
                gender = g,
                weightKg = w,
                heightCm = h,
                activityLevel = level,
                goal = goalValue
            )
            return TdeeCalculator.calculate(tempProfile)
        }
}