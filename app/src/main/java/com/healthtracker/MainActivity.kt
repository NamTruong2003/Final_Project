package com.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.data.repository.SettingsRepository
import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import com.healthtracker.navigation.AppNavHost
import com.healthtracker.ui.theme.HealthTrackerTheme
import com.healthtracker.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsRepository: SettingsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runBlocking {
            val savedLang = settingsRepository.observeLanguage().first()
            LocaleHelper.setAppLanguage(savedLang)
        }
        setContent {
            val themeBrightness by settingsRepository.observeThemeBrightness().collectAsStateWithLifecycle(initialValue = ThemeBrightness.SYSTEM)
            val colorPalette by settingsRepository.observeColorPalette().collectAsStateWithLifecycle(initialValue = ColorPalette.GREEN)
            val fontSize by settingsRepository.observeFontSize()
                .collectAsStateWithLifecycle(initialValue = FontSize.MEDIUM)
            val darkTheme = when (themeBrightness) {
                ThemeBrightness.LIGHT -> false
                ThemeBrightness.DARK -> true
                ThemeBrightness.SYSTEM -> isSystemInDarkTheme()
            }
            HealthTrackerTheme (darkTheme = darkTheme,colorPalette = colorPalette, fontSize = fontSize) {
                AppNavHost()
            }
        }
    }
}

