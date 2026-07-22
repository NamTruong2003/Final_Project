package com.healthtracker.ui.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.R
import com.healthtracker.ui.components.BottomNavBar
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.statistics.components.CaloriesBarChart
import com.healthtracker.ui.statistics.components.CaloriesLineChart
import com.healthtracker.ui.statistics.components.PeriodSegmentedControl
import com.healthtracker.ui.statistics.components.StatSummaryCards
import com.healthtracker.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel(),
    onTabSelected: (BottomNavTab) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.statistics_title),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(currentTab = BottomNavTab.STATS, onTabSelected = onTabSelected)
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
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceXL)
            ) {
                Spacer(Modifier.height(Dimens.spaceS))

                PeriodSegmentedControl(selected = uiState.period, onSelect = viewModel::onPeriodChange)

                Column {
                    Text(
                        text = stringResource(R.string.statistics_calories_in_chart),
                        fontSize = Dimens.textL,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(Dimens.spaceS))
                    CaloriesBarChart(stats = uiState.chartStats, goalCalories = uiState.currentGoal)
                }

                Column {
                    Text(
                        text = stringResource(R.string.statistics_calories_out_chart),
                        fontSize = Dimens.textL,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(Dimens.spaceS))
                    CaloriesLineChart(stats = uiState.chartStats)
                }

                StatSummaryCards(
                    averageCaloriesIn = uiState.averageCaloriesIn,
                    averageCaloriesOut = uiState.averageCaloriesOut,
                    daysGoalMet = uiState.daysGoalMet,
                    totalDays = uiState.totalDays
                )

                Spacer(Modifier.height(Dimens.spaceL))
            }
        }
    }
}