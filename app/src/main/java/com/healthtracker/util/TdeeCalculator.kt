package com.healthtracker.util

import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Gender
import com.healthtracker.model.Goal
import com.healthtracker.model.UserProfile

object TdeeCalculator {

    fun calculateBmr(profile: UserProfile): Double {
        val base = 10 * profile.weightKg + 6.25 * profile.heightCm - 5 * profile.age
        return if (profile.gender == Gender.MALE) base + 5 else base - 161
    }

    fun calculate(profile: UserProfile): Int {
        val bmr = calculateBmr(profile)
        val activityFactor = when (profile.activityLevel) {
            ActivityLevel.SEDENTARY -> ActivityLevel.SEDENTARY.factor
            ActivityLevel.LIGHT -> ActivityLevel.LIGHT.factor
            ActivityLevel.MODERATE -> ActivityLevel.MODERATE.factor
            ActivityLevel.ACTIVE -> ActivityLevel.ACTIVE.factor
            ActivityLevel.VERY_ACTIVE -> ActivityLevel.ACTIVE.factor
        }
        val tdee = bmr * activityFactor
        val adjusted = when (profile.goal) {
            Goal.LOSE_WEIGHT -> tdee + Goal.LOSE_WEIGHT.adjustment
            Goal.MAINTAIN_WEIGHT -> tdee + Goal.MAINTAIN_WEIGHT.adjustment
            Goal.GAIN_WEIGHT -> tdee + Goal.MAINTAIN_WEIGHT.adjustment
        }
        return adjusted.toInt()
    }
}