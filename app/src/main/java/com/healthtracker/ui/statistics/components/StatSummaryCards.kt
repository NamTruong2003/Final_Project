package com.healthtracker.ui.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens

@Composable
fun StatSummaryCards(
    averageCaloriesIn: Int,
    averageCaloriesOut: Int,
    daysGoalMet: Int,
    totalDays: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
        StatCard(
            icon = Icons.Filled.Restaurant,
            iconBg = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            iconTint = MaterialTheme.colorScheme.primary,
            label = stringResource(R.string.statistics_avg_in),
            value = "$averageCaloriesIn",
            unit = stringResource(R.string.dashboard_kcal_unit),
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
            StatCard(
                icon = Icons.Filled.LocalFireDepartment,
                iconBg = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                iconTint = MaterialTheme.colorScheme.tertiary,
                label = stringResource(R.string.statistics_avg_out),
                value = "$averageCaloriesOut",
                unit = stringResource(R.string.dashboard_kcal_unit),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = Icons.Filled.EmojiEvents,
                iconBg = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                iconTint = MaterialTheme.colorScheme.secondary,
                label = stringResource(R.string.statistics_days_goal_met),
                value = "$daysGoalMet/$totalDays",
                unit = stringResource(R.string.statistics_days_unit),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    iconBg: androidx.compose.ui.graphics.Color,
    iconTint: androidx.compose.ui.graphics.Color,
    label: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(Dimens.spaceM)
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(Dimens.statCardIconSize)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(Dimens.spaceXS))
            Text(text = label, fontSize = Dimens.textS, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(Dimens.spaceXS))
        Row(verticalAlignment = androidx.compose.ui.Alignment.Bottom) {
            Text(text = value, fontSize = Dimens.textL, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.width(4.dp))
            Text(
                text = unit,
                fontSize = Dimens.textXS,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
    }
}