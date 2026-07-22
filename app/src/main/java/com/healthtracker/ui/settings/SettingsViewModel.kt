package com.healthtracker.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.SettingsRepository
import com.healthtracker.data.repository.UserProfileRepository
import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import com.healthtracker.util.BmiCalculator
import com.healthtracker.util.LocaleHelper
import com.healthtracker.util.classifyBmi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        userProfileRepository.observeProfile(),
        settingsRepository.observeLanguage(),
        settingsRepository.observeThemeBrightness(),
        settingsRepository.observeColorPalette(),
        settingsRepository.observeFontSize()
    ) { profile, language, brightness, palette, fontSize ->
        val bmi = if (profile != null) BmiCalculator.calculate(profile.weightKg, profile.heightCm) else 0.0
        SettingsUiState(
            isLoading = false,
            userName = profile?.fullName ?: "",
            bmi = bmi,
            bmiCategory = classifyBmi(bmi),
            language = language,
            themeBrightness = brightness,
            colorPalette = palette,
            fontSize = fontSize
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun onLanguageChange(langCode: String) {
        viewModelScope.launch {
            settingsRepository.setLanguage(langCode)
            LocaleHelper.setAppLanguage(langCode)
        }
    }

    fun onBrightnessChange(brightness: ThemeBrightness) {
        viewModelScope.launch { settingsRepository.setThemeBrightness(brightness) }
    }

    fun onColorPaletteChange(palette: ColorPalette) {
        viewModelScope.launch { settingsRepository.setColorPalette(palette) }
    }

    fun onFontSizeChange(fontSize: FontSize) {
        viewModelScope.launch { settingsRepository.setFontSize(fontSize) }
    }
}