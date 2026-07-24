package com.healthtracker.ui.activitydiary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.R
import com.healthtracker.ui.activitydiary.components.AddActivityBottomSheet
import com.healthtracker.ui.activitydiary.components.ActivityListCard
import com.healthtracker.ui.components.BottomNavBar
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.dashboard.components.DateSelector
import com.healthtracker.ui.theme.Dimens
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDiaryScreen(
    viewModel: ActivityDiaryViewModel = hiltViewModel(),
    onTabSelected: (BottomNavTab) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.activity_diary_title),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::openAddSheet,
                shape = RoundedCornerShape(Dimens.radiusL),
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.activity_add_title))
            }
        },
        bottomBar = {
            BottomNavBar(currentTab = BottomNavTab.ACTIVITY, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimens.spaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceM)
            ) {
                Spacer(Modifier.height(Dimens.spaceS))

                DateSelector(
                    date = uiState.date,
                    onPreviousDay = viewModel::onPreviousDay,
                    onNextDay = viewModel::onNextDay
                )

                Spacer(Modifier.height(Dimens.spaceXS))

                if (uiState.activities.isEmpty()) {
                    Text(
                        text = stringResource(R.string.activity_no_items),
                        fontSize = Dimens.textS,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(Dimens.spaceM)
                    )
                } else {
                    uiState.activities.forEach { activity ->
                        ActivityListCard(
                            activity = activity,
                            onDelete = { viewModel.onDeleteActivity(activity.id) }
                        )
                    }
                }

                Spacer(Modifier.height(Dimens.spaceM))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.radiusM))
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                        .padding(Dimens.spaceM),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.activity_diary_total_burned),
                        fontSize = Dimens.textM,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${uiState.totalCaloriesBurned} ${stringResource(R.string.dashboard_kcal_unit)}",
                        fontSize = Dimens.textM,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Spacer(Modifier.height(Dimens.spaceXL))
            }
        }
    }

    if (uiState.isAddSheetOpen) {
        AddActivityBottomSheet(
            uiState = uiState,
            onDismiss = viewModel::closeAddSheet,
            onSelectType = viewModel::onSelectActivityType,
            onDurationChange = viewModel::onDurationChange,
            onConfirm = viewModel::onConfirmAdd
        )
    }
}