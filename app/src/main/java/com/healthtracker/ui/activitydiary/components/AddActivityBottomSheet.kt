package com.healthtracker.ui.activitydiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthtracker.R
import com.healthtracker.model.ActivityType
import com.healthtracker.ui.activitydiary.ActivityDiaryUiState
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.util.activityIconFor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityBottomSheet(
    uiState: ActivityDiaryUiState,
    onDismiss: () -> Unit,
    onSelectType: (ActivityType) -> Unit,
    onDurationChange: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val configuration = LocalConfiguration.current
    val sheetHeight = configuration.screenHeightDp.dp * 0.8f

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .padding(horizontal = Dimens.spaceL)
                .padding(bottom = Dimens.spaceL)
        ) {
            Text(
                text = stringResource(R.string.activity_add_title),
                fontSize = Dimens.textL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.spaceM))

            Text(
                text = stringResource(R.string.activity_select_type),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Dimens.spaceS))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spaceS),
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceS)
            ) {
                items(uiState.activityTypes) { type ->
                    ActivityTypeChip(
                        type = type,
                        selected = uiState.selectedActivityType?.id == type.id,
                        onClick = { onSelectType(type) }
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spaceM))

            Text(
                text = stringResource(R.string.activity_duration_minutes),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Dimens.spaceS))

            DurationStepper(
                minutes = uiState.durationMinutes,
                onChange = onDurationChange
            )

            Spacer(Modifier.height(Dimens.spaceM))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.radiusM))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(Dimens.spaceM),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.activity_estimated_burn),
                    fontSize = Dimens.textS,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${uiState.estimatedCaloriesForSelection}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.dashboard_kcal_unit),
                        fontSize = Dimens.textS,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spaceM))

            Button(
                onClick = onConfirm,
                enabled = uiState.selectedActivityType != null && !uiState.isSaving,
                shape = RoundedCornerShape(Dimens.radiusM),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Filled.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(Dimens.spaceXS))
                    Text(stringResource(R.string.activity_save))
                }
            }
        }
    }
}

@Composable
private fun ActivityTypeChip(type: ActivityType, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(Dimens.activityTypeChipSize)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(
                if (selected) MaterialTheme.colorScheme.tertiaryContainer
                else MaterialTheme.colorScheme.surfaceContainer
            )
            .clickableNoRipple(onClick)
            .padding(Dimens.spaceXS)
    ) {
        Icon(
            activityIconFor(type.iconName),
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = type.name,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun DurationStepper(minutes: Int, onChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(Dimens.spaceXS),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(Dimens.radiusS))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickableNoRipple { onChange(minutes - 5) },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Remove, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }

        Text(
            text = "$minutes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(Dimens.radiusS))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickableNoRipple { onChange(minutes + 5) },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}