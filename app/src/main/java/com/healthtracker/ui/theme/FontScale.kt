package com.healthtracker.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.healthtracker.model.FontSize

val LocalFontScale = compositionLocalOf { 1f }

fun FontSize.toScale(): Float = when (this) {
    FontSize.SMALL -> 0.9f
    FontSize.MEDIUM -> 1.0f
    FontSize.LARGE -> 1.15f
}