package com.healthtracker.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.ActivityRepository
import com.healthtracker.data.repository.MealRepository
import com.healthtracker.data.repository.UserProfileRepository
import com.healthtracker.model.DayStat
import com.healthtracker.util.TdeeCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val activityRepository: ActivityRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    init {
        loadStats(StatisticsPeriod.WEEK)
    }

    fun onPeriodChange(period: StatisticsPeriod) {
        loadStats(period)
    }
    fun refresh() {
        loadStats(_uiState.value.period)
    }

    private fun loadStats(period: StatisticsPeriod) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, period = period) }

            val profile = userProfileRepository.getProfile()
            val goal = profile?.let { TdeeCalculator.calculate(it) } ?: 0

            val daysToLoad = if (period == StatisticsPeriod.WEEK) 7 else 30
            val today = LocalDate.now()

            val rawStats = (0 until daysToLoad).map { offset ->
                val date = today.minusDays((daysToLoad - 1 - offset).toLong())
                val caloriesIn = mealRepository.getTotalCaloriesByDate(date)
                val caloriesOut = activityRepository.getTotalCaloriesByDate(date)
                DayStat(date = date, caloriesIn = caloriesIn, caloriesOut = caloriesOut, goalCalories = goal)
            }

            _uiState.update { it.copy(isLoading = false, dayStats = rawStats) }
        }
    }
}