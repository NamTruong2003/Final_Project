package com.healthtracker.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

fun activityIconFor(iconName: String?): ImageVector = when (iconName) {
    "activity_walking" -> Icons.AutoMirrored.Filled.DirectionsWalk
    "activity_walking_fast" -> Icons.AutoMirrored.Filled.DirectionsWalk
    "activity_running" -> Icons.AutoMirrored.Filled.DirectionsRun
    "activity_cycling" -> Icons.Filled.DirectionsBike
    "activity_swimming" -> Icons.Filled.Pool
    "activity_swimming_light" -> Icons.Filled.Pool
    "activity_yoga" -> Icons.Filled.SelfImprovement
    "activity_gym" -> Icons.Filled.FitnessCenter
    "activity_stairs" -> Icons.Filled.Stairs
    "activity_jumprope" -> Icons.Filled.SportsGymnastics
    "activity_badminton" -> Icons.Filled.SportsTennis
    "activity_football" -> Icons.Filled.SportsSoccer
    "activity_basketball" -> Icons.Filled.SportsBasketball
    "activity_volleyball" -> Icons.Filled.SportsVolleyball
    "activity_aerobic" -> Icons.Filled.SportsGymnastics
    "activity_zumba" -> Icons.Filled.SportsGymnastics
    "activity_shuttlecock" -> Icons.Filled.SportsTennis
    "activity_housework" -> Icons.Filled.CleaningServices
    else -> Icons.Filled.FitnessCenter
}