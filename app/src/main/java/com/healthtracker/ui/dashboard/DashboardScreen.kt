package com.healthtracker.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.ui.components.BottomNavBar
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.dashboard.components.AdviceBanner
import com.healthtracker.ui.dashboard.components.BalanceCard
import com.healthtracker.ui.dashboard.components.CalorieRingCard
import com.healthtracker.ui.dashboard.components.DashboardTopBar
import com.healthtracker.ui.dashboard.components.DateSelector
import com.healthtracker.ui.dashboard.components.QuickActionButtons
import com.healthtracker.ui.dashboard.components.SummaryCardsGrid
import com.healthtracker.ui.theme.Dimens

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onAddMealClick: () -> Unit,
    onAddActivityClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTabSelected: (BottomNavTab) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { DashboardTopBar(onSettingsClick = onSettingsClick) },
        bottomBar = {
            BottomNavBar(currentTab = BottomNavTab.HOME, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimens.spaceL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceL)
        ) {
            Spacer(Modifier.height(Dimens.spaceXS))

            DateSelector(
                date = uiState.date,
                onPreviousDay = { viewModel.loadDashboard(uiState.date.minusDays(1)) },
                onNextDay = { viewModel.loadDashboard(uiState.date.plusDays(1)) }
            )

            CalorieRingCard(
                goalCalories = uiState.goalCalories,
                caloriesIn = uiState.caloriesIn,
                caloriesOut = uiState.caloriesOut,
                remainingCalories = uiState.remainingCalories
            )

            SummaryCardsGrid(caloriesIn = uiState.caloriesIn, caloriesOut = uiState.caloriesOut)

            BalanceCard(netCalories = uiState.netCalories)

            AdviceBanner(advice = uiState.advice)

            QuickActionButtons(
                onAddMealClick = onAddMealClick,
                onAddActivityClick = onAddActivityClick
            )

            Spacer(Modifier.height(Dimens.spaceL))
        }
    }
}