package com.healthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.healthtracker.data.repository.SettingsRepository
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
            HealthTrackerTheme {
                AppNavHost()
            }
        }
    }
}

