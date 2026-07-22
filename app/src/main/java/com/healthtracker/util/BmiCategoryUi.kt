package com.healthtracker.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.healthtracker.R

enum class BmiCategory { UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE }

fun classifyBmi(bmi: Double): BmiCategory = when {
    bmi < 18.5 -> BmiCategory.UNDERWEIGHT
    bmi < 25.0 -> BmiCategory.NORMAL
    bmi < 30.0 -> BmiCategory.OVERWEIGHT
    else -> BmiCategory.OBESE
}

@Composable
fun BmiCategory.displayName(): String = when (this) {
    BmiCategory.UNDERWEIGHT -> stringResource(R.string.bmi_underweight)
    BmiCategory.NORMAL -> stringResource(R.string.bmi_normal)
    BmiCategory.OVERWEIGHT -> stringResource(R.string.bmi_overweight)
    BmiCategory.OBESE -> stringResource(R.string.bmi_obese)
}