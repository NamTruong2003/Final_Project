package com.healthtracker.data.repository

import com.healthtracker.data.datastore.SettingsDataStore
import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) {
    fun observeLanguage(): Flow<String> = settingsDataStore.language
    suspend fun setLanguage(langCode: String) = settingsDataStore.setLanguage(langCode)

    fun observeThemeBrightness(): Flow<ThemeBrightness> = settingsDataStore.themeBrightness
    suspend fun setThemeBrightness(brightness: ThemeBrightness) =
        settingsDataStore.setThemeBrightness(brightness)


    fun observeFontSize(): Flow<FontSize> = settingsDataStore.fontSize
    suspend fun setFontSize(size: FontSize) = settingsDataStore.setFontSize(size)

    fun observeIsOnboarded(): Flow<Boolean> = settingsDataStore.isOnboarded
    suspend fun setOnboarded(value: Boolean) = settingsDataStore.setOnboarded(value)
    fun observeColorPalette(): Flow<ColorPalette> = settingsDataStore.colorPalette
    suspend fun setColorPalette(palette: ColorPalette) = settingsDataStore.setColorPalette(palette)
}