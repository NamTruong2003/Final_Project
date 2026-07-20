package com.healthtracker.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.R
import com.healthtracker.model.ActivityLevel
import com.healthtracker.model.Goal
import com.healthtracker.ui.onboarding.components.*
import com.healthtracker.ui.theme.Dimens
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { OnboardingTopBar(progress = uiState.progress) },
        bottomBar = {
            OnboardingBottomCta(
                isSaving = uiState.isSaving,
                onClick = { viewModel.onSaveProfile(onSuccess = onFinished) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.spaceL)
        ) {
            Spacer(Modifier.height(Dimens.spaceXS))

            Text(
                text = stringResource(R.string.onboarding_title),
                fontSize = Dimens.textXL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(Dimens.spaceXXS))
            Text(
                text = stringResource(R.string.onboarding_subtitle),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(Dimens.spaceXL))

            LabeledField(label = stringResource(R.string.label_full_name)) {
                DesignTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    placeholder = stringResource(R.string.placeholder_full_name)
                )
            }
            uiState.nameError?.let { ErrorText(stringResource(it)) }

            Spacer(Modifier.height(Dimens.spaceL))

            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                Column(modifier = Modifier.weight(1f)) {
                    LabeledField(label = stringResource(R.string.label_date_of_birth)) {
                        DateField(
                            dateText = uiState.dateOfBirth
                                ?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                            placeholder = stringResource(R.string.placeholder_date),
                            onClick = { showDatePicker = true }
                        )
                    }
                    uiState.dateOfBirthError?.let { ErrorText(stringResource(it)) }
                }
                Column(modifier = Modifier.weight(1f)) {
                    LabeledField(label = stringResource(R.string.label_gender)) {
                        GenderSegmentedControl(
                            selected = uiState.gender,
                            onSelect = viewModel::onGenderChange
                        )
                    }
                }
            }

            Spacer(Modifier.height(Dimens.spaceL))

            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                Column(modifier = Modifier.weight(1f)) {
                    LabeledField(label = stringResource(R.string.label_weight)) {
                        StepperField(value = uiState.weightKg, onValueChange = viewModel::onWeightChange, step = 1.0)
                    }
                    uiState.weightError?.let { ErrorText(stringResource(it)) }
                }
                Column(modifier = Modifier.weight(1f)) {
                    LabeledField(label = stringResource(R.string.label_height)) {
                        StepperField(value = uiState.heightCm, onValueChange = viewModel::onHeightChange, step = 1.0)
                    }
                    uiState.heightError?.let { ErrorText(stringResource(it)) }
                }
            }

            Spacer(Modifier.height(Dimens.spaceXXL))

            Text(
                text = stringResource(R.string.section_activity_level),
                fontSize = Dimens.textL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.spaceS))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                items(ActivityLevel.entries.toList()) { level ->
                    ActivityLevelCard(
                        level = level,
                        selected = uiState.activityLevel == level,
                        onClick = { viewModel.onActivityLevelChange(level) }
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spaceXL))

            Text(
                text = stringResource(R.string.section_goal),
                fontSize = Dimens.textL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.spaceS))

            Column(verticalArrangement = Arrangement.spacedBy(Dimens.spaceS)) {
                Goal.entries.forEach { goal ->
                    GoalOptionRow(
                        goal = goal,
                        selected = uiState.goal == goal,
                        onClick = { viewModel.onGoalChange(goal) }
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spaceXXXL))
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
                        viewModel.onBirthChange(date)
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Hủy") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}