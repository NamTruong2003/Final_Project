package com.healthtracker.ui.activitydiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.ActivityRepository
import com.healthtracker.data.repository.ActivityTypeRepository
import com.healthtracker.model.ActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ActivityDiaryViewModel @Inject constructor(
    private val activityRepository: ActivityRepository,
    private val activityTypeRepository: ActivityTypeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityDiaryUiState())
    val uiState: StateFlow<ActivityDiaryUiState> = _uiState.asStateFlow()

    private var activitiesJob: Job? = null

    init {
        loadActivityTypes()
        loadDate(LocalDate.now())
    }

    private fun loadActivityTypes() {
        viewModelScope.launch {
            val types = activityTypeRepository.observeAllActivityTypes().first()
            _uiState.update {
                it.copy(
                    activityTypes = types,
                    selectedActivityType = types.firstOrNull()
                )
            }
        }
    }

    fun loadDate(date: LocalDate) {
        _uiState.update { it.copy(date = date, isLoading = true) }
        activitiesJob?.cancel()
        activitiesJob = activityRepository.observeActivitiesWithTypeByDate(date)
            .onEach { list -> _uiState.update { it.copy(isLoading = false, activities = list) } }
            .launchIn(viewModelScope)
    }

    fun onPreviousDay() = loadDate(_uiState.value.date.minusDays(1))
    fun onNextDay() = loadDate(_uiState.value.date.plusDays(1))

    fun openAddSheet() {
        _uiState.update {
            it.copy(
                isAddSheetOpen = true,
                selectedActivityType = it.activityTypes.firstOrNull(),
                durationMinutes = 30
            )
        }
    }

    fun closeAddSheet() {
        _uiState.update { it.copy(isAddSheetOpen = false) }
    }

    fun onSelectActivityType(type: ActivityType) {
        _uiState.update { it.copy(selectedActivityType = type) }
    }

    fun onDurationChange(minutes: Int) {
        val clamped = minutes.coerceIn(1, 999)
        _uiState.update { it.copy(durationMinutes = clamped) }
    }

    fun onConfirmAdd() {
        val state = _uiState.value
        val type = state.selectedActivityType ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            activityRepository.addActivityEntry(
                activityTypeId = type.id,
                durationMinutes = state.durationMinutes,
                date = state.date
            )
            _uiState.update { it.copy(isSaving = false, isAddSheetOpen = false) }
        }
    }

    fun onDeleteActivity(id: Int) {
        viewModelScope.launch {
            activityRepository.deleteActivity(id)
        }
    }
}