package com.healthtracker.util

sealed class AdviceResult {
    data class UnderGoal(val remainingKcal: Int) : AdviceResult()
    data object OnTrack : AdviceResult()
    data class OverGoal(val overKcal: Int) : AdviceResult()
}

object AdviceGenerator {
    fun generate(goalCalories: Int, netCalories: Int): AdviceResult {
        val diff = goalCalories - netCalories
        return when {
            diff > 50 -> AdviceResult.UnderGoal(remainingKcal = diff)
            diff < -50 -> AdviceResult.OverGoal(overKcal = -diff)
            else -> AdviceResult.OnTrack
        }
    }
}