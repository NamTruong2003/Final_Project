package com.healthtracker.util

import java.time.LocalDate
import java.time.Period

object Validators{
    fun isValidName(name:String): Boolean{
        return !name.isNullOrBlank()
    }
    fun isValidWeight(weightKg: Double): Boolean{
        return weightKg in 20.0..150.0
    }
    fun isValidHeight(heightCm: Double): Boolean{
        return heightCm in 50.0..250.0
    }
    fun isValidDateOfBirth(date: LocalDate): Boolean{
        val today = LocalDate.now()
        if(date.isAfter(today))return false

        val age = Period.between(date,today).years
        return age in 6..100
    }
}