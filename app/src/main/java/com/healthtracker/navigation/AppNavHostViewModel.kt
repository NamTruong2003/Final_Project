package com.healthtracker.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppNavHostViewModel @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {

    val isOnboarded: StateFlow<Boolean?> = settingsRepository.observeIsOnboarded()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}