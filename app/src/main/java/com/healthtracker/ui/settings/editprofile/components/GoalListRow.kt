package com.healthtracker.ui.settings.editprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.model.Goal
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun GoalListSection(selected: Goal?, onSelect: (Goal) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Goal.entries.forEachIndexed { index, goal ->
            GoalRow(goal = goal, selected = selected == goal, onClick = { onSelect(goal) })
            if (index != Goal.entries.lastIndex) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            }
        }
    }
}

@Composable
private fun GoalRow(goal: Goal, selected: Boolean, onClick: () -> Unit) {
    val icon = when (goal) {
        Goal.LOSE_WEIGHT -> Icons.AutoMirrored.Filled.TrendingDown
        Goal.MAINTAIN_WEIGHT -> Icons.Filled.DragHandle
        Goal.GAIN_WEIGHT -> Icons.AutoMirrored.Filled.TrendingUp
    }
    val labelRes = when (goal) {
        Goal.LOSE_WEIGHT -> R.string.goal_lose_weight
        Goal.MAINTAIN_WEIGHT -> R.string.goal_maintain
        Goal.GAIN_WEIGHT -> R.string.goal_gain_weight
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f) else Color.Transparent)
            .clickableNoRipple(onClick)
            .padding(Dimens.spaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Dimens.radiusS))
                .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(Dimens.spaceM))
        Text(
            text = stringResource(labelRes),
            fontSize = Dimens.textM,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .border(2.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}

