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
import com.healthtracker.ui.activitydiary.ActivityDiaryScreen
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.dashboard.DashboardScreen
import com.healthtracker.ui.mealdiary.MealDiaryScreen
import com.healthtracker.ui.onboarding.OnboardingScreen
import com.healthtracker.ui.settings.SettingsScreen
import com.healthtracker.ui.settings.editprofile.EditProfileScreen
import com.healthtracker.ui.statistics.StatisticsScreen

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
                        BottomNavTab.ACTIVITY ->navController.navigate(NavRoutes.ACTIVITY_DIARY)
                        BottomNavTab.STATS -> navController.navigate(NavRoutes.STATISTICS)
                        BottomNavTab.PROFILE -> navController.navigate(NavRoutes.SETTINGS)
                    }
                }
            )
        }

        composable(NavRoutes.MEAL_DIARY) {
            MealDiaryScreen(
                onTabSelected = { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> navController.navigate(NavRoutes.DASHBOARD)
                        BottomNavTab.DIARY -> {}
                        BottomNavTab.ACTIVITY ->navController.navigate(NavRoutes.ACTIVITY_DIARY)
                        BottomNavTab.STATS -> navController.navigate(NavRoutes.STATISTICS)
                        BottomNavTab.PROFILE -> navController.navigate(NavRoutes.SETTINGS)
                    }
                }
            )
        }

        composable(NavRoutes.ACTIVITY_DIARY) {
            ActivityDiaryScreen(
                onTabSelected = { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> navController.navigate(NavRoutes.DASHBOARD)
                        BottomNavTab.DIARY ->navController.navigate(NavRoutes.MEAL_DIARY)
                        BottomNavTab.ACTIVITY -> {}
                        BottomNavTab.STATS -> navController.navigate(NavRoutes.STATISTICS)
                        BottomNavTab.PROFILE -> navController.navigate(NavRoutes.SETTINGS)
                    }
                }
            )
        }

        composable(NavRoutes.STATISTICS) {
            StatisticsScreen(
                onTabSelected = { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> navController.navigate(NavRoutes.DASHBOARD)
                        BottomNavTab.DIARY -> navController.navigate(NavRoutes.MEAL_DIARY)
                        BottomNavTab.ACTIVITY ->navController.navigate(NavRoutes.ACTIVITY_DIARY)
                        BottomNavTab.STATS -> {}
                        BottomNavTab.PROFILE -> navController.navigate(NavRoutes.SETTINGS)
                    }
                }
            )
        }

        composable(NavRoutes.SETTINGS) {
            SettingsScreen(
                onEditProfileClick = { navController.navigate(NavRoutes.EDIT_PROFILE) },
                onTabSelected = { tab ->
                    when (tab) {
                        BottomNavTab.HOME -> navController.navigate(NavRoutes.DASHBOARD)
                        BottomNavTab.ACTIVITY ->navController.navigate(NavRoutes.ACTIVITY_DIARY)
                        BottomNavTab.DIARY -> navController.navigate(NavRoutes.MEAL_DIARY)
                        BottomNavTab.STATS -> navController.navigate(NavRoutes.STATISTICS)
                        BottomNavTab.PROFILE -> {}
                    }
                }
            )
        }
        composable(NavRoutes.EDIT_PROFILE) {
            EditProfileScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }
    }
}

