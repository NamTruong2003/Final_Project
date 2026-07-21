package com.healthtracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.dashboard.DashboardScreen
import com.healthtracker.ui.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
    navHostViewModel: AppNavHostViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val isOnboarded by navHostViewModel.isOnboarded.collectAsStateWithLifecycle()


    if (isOnboarded == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val startDestination = if (isOnboarded == true) NavRoutes.DASHBOARD else NavRoutes.ONBOARDING

    NavHost(navController = navController, startDestination = startDestination) {

        composable(NavRoutes.ONBOARDING) {
            OnboardingScreen(onFinished = {
                navController.navigate(NavRoutes.DASHBOARD){
                    popUpTo(NavRoutes.ONBOARDING){inclusive = true}
                }
            })
        }

        composable(NavRoutes.DASHBOARD) {
            DashboardScreen(
                onAddMealClick = { navController.navigate(NavRoutes.MEAL_DIARY) },
                onAddActivityClick = { navController.navigate(NavRoutes.ACTIVITY_DIARY) },
                onSettingsClick = { navController.navigate(NavRoutes.SETTINGS) },
                onTabSelected = { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> {}
                        BottomNavTab.DIARY -> navController.navigate(NavRoutes.MEAL_DIARY)
                        BottomNavTab.STATS -> navController.navigate(NavRoutes.STATISTICS)
                        BottomNavTab.PROFILE -> navController.navigate(NavRoutes.SETTINGS)
                    }
                }
            )
        }

        composable(NavRoutes.MEAL_DIARY) {
            PlaceholderScreen("Meal Diary Screen")
        }

        composable(NavRoutes.ACTIVITY_DIARY) {
            PlaceholderScreen("Activity Diary Screen")
        }

        composable(NavRoutes.STATISTICS) {
            PlaceholderScreen("Statistics Screen")
        }

        composable(NavRoutes.SETTINGS) {
            PlaceholderScreen("Settings Screen")
        }
    }
}

@Composable
private fun PlaceholderScreen(name: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name)
    }
}