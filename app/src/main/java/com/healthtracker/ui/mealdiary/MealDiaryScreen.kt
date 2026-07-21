package com.healthtracker.ui.mealdiary

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
import com.healthtracker.model.MealType
import com.healthtracker.ui.components.BottomNavBar
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.dashboard.components.DateSelector
import com.healthtracker.ui.mealdiary.components.AddFoodBottomSheet
import com.healthtracker.ui.mealdiary.components.MealSectionCard
import com.healthtracker.ui.mealdiary.components.StickySummaryBar
import com.healthtracker.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDiaryScreen(
    viewModel: MealDiaryViewModel = hiltViewModel(),
    onTabSelected: (BottomNavTab) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.meal_diary_title),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },

        bottomBar = {
            Column {
                StickySummaryBar(
                    totalCalories = uiState.totalCalories,
                    goalCalories = uiState.goalCalories,
                    progressFraction = uiState.progressFraction
                )
                BottomNavBar(currentTab = BottomNavTab.DIARY, onTabSelected = onTabSelected)
            }
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

                MealType.entries.forEach { type ->
                    MealSectionCard(
                        mealType = type,
                        items = uiState.mealsByType[type].orEmpty(),
                        totalCalories = uiState.caloriesForType(type),
                        onAddClick = { viewModel.openAddSheet(type) },
                        onDeleteItem = viewModel::onDeleteMeal
                    )
                }

                Spacer(Modifier.height(Dimens.spaceXL))
            }
        }
    }

    if (uiState.isAddSheetOpen) {
        AddFoodBottomSheet(
            uiState = uiState,
            onDismiss = viewModel::closeAddSheet,
            onSelectTab = viewModel::onSelectTab,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onSelectFood = viewModel::onSelectFood,
            onQuantityChange = viewModel::onQuantityChange,
            onCustomNameChange = viewModel::onCustomNameChange,
            onCustomCaloriesChange = viewModel::onCustomCaloriesChange,
            onConfirm = viewModel::onConfirmAdd
        )
    }
}