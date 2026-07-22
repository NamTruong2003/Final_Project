package com.healthtracker.ui.statistics

import com.healthtracker.model.DayStat

data class StatisticsUiState(
    val isLoading: Boolean = false,
    val period: StatisticsPeriod = StatisticsPeriod.WEEK,
    val dayStats: List<DayStat> = emptyList()
) {
    val averageCaloriesIn: Int
        get() = if (dayStats.isEmpty()) 0 else dayStats.sumOf { it.caloriesIn } / dayStats.size

    val averageCaloriesOut: Int
        get() = if (dayStats.isEmpty()) 0 else dayStats.sumOf { it.caloriesOut } / dayStats.size

    val daysGoalMet: Int
        get() = dayStats.count { it.metGoal }

    val totalDays: Int
        get() = dayStats.size

    val currentGoal: Int
        get() = dayStats.lastOrNull()?.goalCalories ?: 0

    val chartStats: List<ChartPoint>
        get() = if (period == StatisticsPeriod.WEEK) {
            dayStats.map { ChartPoint(label = it.date, caloriesIn = it.caloriesIn, caloriesOut = it.caloriesOut, isWeekly = false) }
        } else {
            dayStats.chunked(7).mapIndexed { index, week ->
                ChartPoint(
                    label = week.last().date,
                    caloriesIn = week.sumOf { it.caloriesIn } / week.size,
                    caloriesOut = week.sumOf { it.caloriesOut } / week.size,
                    isWeekly = true,
                    weekIndex = index + 1
                )
            }
        }
}

data class ChartPoint(
    val label: java.time.LocalDate,
    val caloriesIn: Int,
    val caloriesOut: Int,
    val isWeekly: Boolean,
    val weekIndex: Int = 0
) {
    fun displayLabel(): String {
        return if (isWeekly) "T$weekIndex" else label.dayOfWeek.getDisplayName(
            java.time.format.TextStyle.SHORT, java.util.Locale("vi")
        )
    }
}