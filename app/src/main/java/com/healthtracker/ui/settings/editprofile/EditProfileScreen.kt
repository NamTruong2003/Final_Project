package com.healthtracker.ui.settings.editprofile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.R
import com.healthtracker.model.ActivityLevel
import com.healthtracker.ui.onboarding.components.DateField
import com.healthtracker.ui.onboarding.components.DesignTextField
import com.healthtracker.ui.onboarding.components.GenderSegmentedControl
import com.healthtracker.ui.onboarding.components.LabeledField
import com.healthtracker.ui.onboarding.components.ErrorText
import com.healthtracker.ui.onboarding.components.StepperField
import com.healthtracker.ui.onboarding.components.ActivityLevelCard
import com.healthtracker.ui.settings.editprofile.components.GoalListSection
import com.healthtracker.ui.settings.editprofile.components.TdeeBmiSummaryCard
import com.healthtracker.ui.theme.Dimens
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val successMessage = stringResource(R.string.save_success)

    LaunchedEffect(Unit) {
        viewModel.saveSuccessEvent.collect {
            snackbarHostState.showSnackbar(successMessage)
            onSaved()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_profile_title),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.onSave() }, enabled = !uiState.isSaving) {
                        Text(
                            stringResource(R.string.edit_profile_save),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimens.spaceL)
            ) {
                Spacer(Modifier.height(Dimens.spaceM))

                LabeledField(label = stringResource(R.string.label_full_name)) {
                    DesignTextField(
                        value = uiState.name,
                        onValueChange = viewModel::onNameChange,
                        placeholder = stringResource(R.string.placeholder_full_name)
                    )
                }
                uiState.nameError?.let { ErrorText(stringResource(it)) }

                Spacer(Modifier.height(Dimens.spaceM))

                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                    Column(modifier = Modifier.weight(1f)) {
                        LabeledField(label = stringResource(R.string.label_date_of_birth)) {
                            DateField(
                                dateText = uiState.dateOfBirth?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                    ?: "",
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

                Spacer(Modifier.height(Dimens.spaceM))

                Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                    Column(modifier = Modifier.weight(1f)) {
                        LabeledField(label = stringResource(R.string.label_weight)) {
                            StepperField(
                                value = uiState.weightKg,
                                onValueChange = viewModel::onWeightChange,
                                step = 1.0
                            )
                        }
                        uiState.weightError?.let { ErrorText(stringResource(it)) }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LabeledField(label = stringResource(R.string.label_height)) {
                            StepperField(
                                value = uiState.heightCm,
                                onValueChange = viewModel::onHeightChange,
                                step = 1.0
                            )
                        }
                        uiState.heightError?.let { ErrorText(stringResource(it)) }
                    }
                }

                Spacer(Modifier.height(Dimens.spaceXL))

                Text(
                    text = stringResource(R.string.section_activity_level),
                    fontSize = Dimens.textL,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(Dimens.spaceS))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
                    items(ActivityLevel.entries) { level ->
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
                GoalListSection(selected = uiState.goal, onSelect = viewModel::onGoalChange)

                Spacer(Modifier.height(Dimens.spaceXL))

                TdeeBmiSummaryCard(tdee = uiState.previewTdee, bmi = uiState.previewBmi)

                Spacer(Modifier.height(Dimens.spaceXXL))

                Button(
                    onClick = { viewModel.onSave() },
                    enabled = !uiState.isSaving,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.radiusM),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.buttonHeight)
                ) {
                    if (uiState.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(stringResource(R.string.edit_profile_save_changes))
                    }
                }

                Spacer(Modifier.height(Dimens.spaceL))
            }
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