package com.healthtracker.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BakeryDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Icecream
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.healthtracker.R
import com.healthtracker.model.MealType

fun MealType.icon(): ImageVector = when (this) {
    MealType.BREAKFAST -> Icons.Filled.BakeryDining
    MealType.LUNCH -> Icons.Filled.Restaurant
    MealType.DINNER -> Icons.Filled.DinnerDining
    MealType.SNACK -> Icons.Filled.Icecream
}

@Composable
fun MealType.displayName(): String = when (this) {
    MealType.BREAKFAST -> stringResource(R.string.meal_breakfast)
    MealType.LUNCH -> stringResource(R.string.meal_lunch)
    MealType.DINNER -> stringResource(R.string.meal_dinner)
    MealType.SNACK -> stringResource(R.string.meal_snack)
}