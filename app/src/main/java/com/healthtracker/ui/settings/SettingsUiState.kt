package com.healthtracker.ui.settings

import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import com.healthtracker.util.BmiCategory

data class SettingsUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val bmi: Double = 0.0,
    val bmiCategory: BmiCategory = BmiCategory.NORMAL,
    val language: String = "vi",
    val themeBrightness: ThemeBrightness = ThemeBrightness.SYSTEM,
    val colorPalette: ColorPalette = ColorPalette.GREEN,
    val fontSize: FontSize = FontSize.MEDIUM,
    val appVersion: String = "1.0.0"
)