package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.ui.theme.SurfaceBlueLight
import com.healthtracker.ui.theme.SurfaceGreenLight

@Composable
fun SummaryCardsGrid(caloriesIn: Int, caloriesOut: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
        SummaryStatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Filled.Restaurant,
            label = stringResource(R.string.dashboard_calories_in_card),
            value = caloriesIn,
            backgroundColor = SurfaceGreenLight,
            accentColor = MaterialTheme.colorScheme.primary
        )
        SummaryStatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            label = stringResource(R.string.dashboard_calories_out_card),
            value = caloriesOut,
            backgroundColor = SurfaceBlueLight,
            accentColor = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun SummaryStatCard(
    modifier: Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: Int,
    backgroundColor: androidx.compose.ui.graphics.Color,
    accentColor: androidx.compose.ui.graphics.Color
) {
    Column(
        modifier = modifier
            .height(Dimens.summaryCardHeight)
            .clip(RoundedCornerShape(Dimens.cardCornerRadiusMedium))
            .background(backgroundColor)
            .padding(Dimens.spaceM),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = accentColor)
            Spacer(Modifier.width(Dimens.spaceXXS))
            Text(text = label, fontSize = Dimens.textS, fontWeight = FontWeight.Medium, color = accentColor)
        }
        Row(verticalAlignment = androidx.compose.ui.Alignment.Bottom) {
            Text(
                text = "$value",
                fontSize = Dimens.textL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.dashboard_kcal_unit),
                fontSize = Dimens.textXS,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}