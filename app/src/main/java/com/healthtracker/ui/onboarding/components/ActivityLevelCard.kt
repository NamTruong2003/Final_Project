package com.healthtracker.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.healthtracker.R
import com.healthtracker.model.ActivityLevel
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun ActivityLevelCard(level: ActivityLevel, selected: Boolean, onClick: () -> Unit) {
    val icon = when (level) {
        ActivityLevel.SEDENTARY -> Icons.Filled.Weekend
        ActivityLevel.LIGHT -> Icons.AutoMirrored.Filled.DirectionsWalk
        ActivityLevel.MODERATE -> Icons.AutoMirrored.Filled.DirectionsRun
        ActivityLevel.ACTIVE -> Icons.Filled.FitnessCenter
        ActivityLevel.VERY_ACTIVE -> Icons.Filled.SportsMartialArts
    }
    val labelRes = when (level) {
        ActivityLevel.SEDENTARY -> R.string.activity_sedentary
        ActivityLevel.LIGHT -> R.string.activity_light
        ActivityLevel.MODERATE -> R.string.activity_moderate
        ActivityLevel.ACTIVE -> R.string.activity_active
        ActivityLevel.VERY_ACTIVE -> R.string.activity_very_active
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(Dimens.activityCardWidth)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(
                width = if (selected) Dimens.borderThick else Dimens.borderThin,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(Dimens.radiusM)
            )
            .clickableNoRipple(onClick)
            .padding(Dimens.spaceM)
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.avatarSize)
                .clip(CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.surfaceContainerLowest else MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(Dimens.spaceXS))
        Text(
            text = stringResource(labelRes),
            fontSize = Dimens.textXS,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}