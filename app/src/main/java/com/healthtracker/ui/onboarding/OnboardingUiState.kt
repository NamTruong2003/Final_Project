package com.healthtracker.ui.onboarding

import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Gender
import com.healthtracker.model.Goal
import java.time.LocalDate

data class OnboardingUiState(
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

    val isSaving: Boolean = false
){
    val progress: Float
        get() {
            var filled = 0
            val total = 7
            if (name.isNotBlank()) filled++
            if (dateOfBirth != null) filled++
            if (gender != null) filled++
            if (weightKg.toDoubleOrNull() != null) filled++
            if (heightCm.toDoubleOrNull() != null) filled++
            if (activityLevel != null) filled++
            if (goal != null) filled++
            return filled.toFloat() / total
        }
}