package com.healthtracker.ui.dashboard

import com.healthtracker.util.AdviceGenerator
import com.healthtracker.util.AdviceResult
import java.time.LocalDate

data class DashboardUiState(
    val isLoading: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val userName: String = "",
    val goalCalories: Int = 0,
    val caloriesIn: Int = 0,
    val caloriesOut: Int = 0
) {
    val netCalories: Int
        get() = caloriesIn - caloriesOut

    val remainingCalories: Int
        get() = goalCalories - netCalories

    val advice: AdviceResult
        get() = AdviceGenerator.generate(goalCalories, netCalories)
}