package com.healthtracker.model

import java.time.LocalDate

data class DayStat(
    val date: LocalDate,
    val caloriesIn: Int,
    val caloriesOut: Int,
    val goalCalories: Int
) {
    val metGoal: Boolean
        get() = goalCalories > 0 && kotlin.math.abs(goalCalories - (caloriesIn - caloriesOut)) <= 100
}