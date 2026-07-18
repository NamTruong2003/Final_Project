package com.healthtracker.data.repository

import com.healthtracker.data.datastore.SettingsDataStore
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

    fun observeThemeColor(): Flow<String> = settingsDataStore.themeColor
    suspend fun setThemeColor(colorName: String) = settingsDataStore.setThemeColor(colorName)

    fun observeFontSize(): Flow<FontSize> = settingsDataStore.fontSize
    suspend fun setFontSize(size: FontSize) = settingsDataStore.setFontSize(size)

    fun observeIsOnboarded(): Flow<Boolean> = settingsDataStore.isOnboarded
    suspend fun setOnboarded(value: Boolean) = settingsDataStore.setOnboarded(value)
}