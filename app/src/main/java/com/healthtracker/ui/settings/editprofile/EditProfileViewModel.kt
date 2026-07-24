package com.healthtracker.ui.settings.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthtracker.data.repository.UserProfileRepository
import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Gender
import com.healthtracker.model.Goal
import com.healthtracker.model.UserProfile
import com.healthtracker.util.Validators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.healthtracker.R
import com.healthtracker.util.toCleanString
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _saveSuccessEvent = MutableSharedFlow<Unit>()
    val saveSuccessEvent: SharedFlow<Unit> = _saveSuccessEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val profile = userProfileRepository.getProfile()
            if (profile != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = profile.fullName,
                        dateOfBirth = profile.dateOfBirth,
                        gender = profile.gender,
                        weightKg = profile.weightKg.toCleanString(),
                        heightCm = profile.heightCm.toCleanString(),
                        activityLevel = profile.activityLevel,
                        goal = profile.goal
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun onBirthChange(birth: LocalDate) {
        _uiState.update { it.copy(dateOfBirth = birth, dateOfBirthError = null) }
    }

    fun onGenderChange(gender: Gender) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun onWeightChange(weightKg: String) {
        _uiState.update { it.copy(weightKg = weightKg, weightError = null) }
    }

    fun onHeightChange(heightCm: String) {
        _uiState.update { it.copy(heightCm = heightCm, heightError = null) }
    }

    fun onActivityLevelChange(activityLevel: ActivityLevel) {
        _uiState.update { it.copy(activityLevel = activityLevel) }
    }

    fun onGoalChange(goal: Goal) {
        _uiState.update { it.copy(goal = goal) }
    }

    fun onSave() {
        val state = _uiState.value

        val dateOfBirth = state.dateOfBirth
        val gender = state.gender
        val weight = state.weightKg.toDoubleOrNull()
        val height = state.heightCm.toDoubleOrNull()
        val activityLevel = state.activityLevel
        val goal = state.goal

        val nameError = if (!Validators.isValidName(state.name)) R.string.error_name_required else null
        val dateOfBirthError = when {
            dateOfBirth == null -> R.string.error_dob_required
            !Validators.isValidDateOfBirth(dateOfBirth) -> R.string.error_dob_invalid
            else -> null
        }
        val weightError = when {
            weight == null -> R.string.error_weight_required
            !Validators.isValidWeight(weight) -> R.string.error_weight_invalid
            else -> null
        }
        val heightError = when {
            height == null -> R.string.error_height_required
            !Validators.isValidHeight(height) -> R.string.error_height_invalid
            else -> null
        }

        if (nameError != null || dateOfBirthError != null || weightError != null || heightError != null ||
            dateOfBirth == null || gender == null || weight == null || height == null ||
            activityLevel == null || goal == null
        ) {
            _uiState.update {
                it.copy(
                    nameError = nameError,
                    dateOfBirthError = dateOfBirthError,
                    weightError = weightError,
                    heightError = heightError
                )
            }
            return
        }

        val updatedProfile = UserProfile(
            fullName = state.name,
            dateOfBirth = dateOfBirth,
            gender = gender,
            weightKg = weight,
            heightCm = height,
            activityLevel = activityLevel,
            goal = goal
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            userProfileRepository.saveProfile(updatedProfile)
            _uiState.update { it.copy(isSaving = false) }
            _saveSuccessEvent.emit(Unit)
        }
    }
}