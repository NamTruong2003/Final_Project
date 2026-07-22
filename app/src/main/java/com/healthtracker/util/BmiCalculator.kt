package com.healthtracker.util

object BmiCalculator {

    fun calculate(weightKg: Double, heightCm: Double): Double {
        val heightM = heightCm / 100
        return weightKg / (heightM * heightM)
    }
}