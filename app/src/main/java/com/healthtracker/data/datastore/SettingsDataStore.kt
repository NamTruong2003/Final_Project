package com.healthtracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_LANGUAGE = stringPreferencesKey("language")
        val KEY_THEME_BRIGHTNESS = stringPreferencesKey("theme_brightness")
        val KEY_COLOR_PALETTE = stringPreferencesKey("color_palette")
        val KEY_FONT_SIZE = stringPreferencesKey("font_size")
        val KEY_IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
    }

    val language: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[KEY_LANGUAGE] ?: "vi" }

    suspend fun setLanguage(langCode: String) {
        context.dataStore.edit { it[KEY_LANGUAGE] = langCode }
    }

    val themeBrightness: Flow<ThemeBrightness> = context.dataStore.data
        .map { prefs ->
            val value = prefs[KEY_THEME_BRIGHTNESS] ?: ThemeBrightness.SYSTEM.name
            ThemeBrightness.valueOf(value)
        }

    suspend fun setThemeBrightness(brightness: ThemeBrightness) {
        context.dataStore.edit { it[KEY_THEME_BRIGHTNESS] = brightness.name }
    }

    val colorPalette: Flow<ColorPalette> = context.dataStore.data
        .map { prefs ->
            val value = prefs[KEY_COLOR_PALETTE] ?: ColorPalette.GREEN.name
            ColorPalette.valueOf(value)
        }

    suspend fun setColorPalette(palette: ColorPalette) {
        context.dataStore.edit { it[KEY_COLOR_PALETTE] = palette.name }
    }

    val fontSize: Flow<FontSize> = context.dataStore.data
        .map { prefs ->
            val value = prefs[KEY_FONT_SIZE] ?: FontSize.MEDIUM.name
            FontSize.valueOf(value)
        }

    suspend fun setFontSize(size: FontSize) {
        context.dataStore.edit { it[KEY_FONT_SIZE] = size.name }
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[KEY_IS_ONBOARDED] ?: false }

    suspend fun setOnboarded(value: Boolean) {
        context.dataStore.edit { it[KEY_IS_ONBOARDED] = value }
    }
}