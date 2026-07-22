package com.healthtracker.model

data class DashboardSummary(
    val goalCalories: Int,
    val caloriesIn: Int,
    val caloriesOut: Int,
    val tipMessage: String = ""
) {

    val remainingCalories: Int
        get() = goalCalories - (caloriesIn - caloriesOut)

    val isOverGoal: Boolean
        get() = remainingCalories < 0
}