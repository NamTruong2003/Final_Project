package com.healthtracker.ui.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.statistics.StatisticsPeriod
import com.healthtracker.ui.theme.Dimens

@Composable
fun PeriodSegmentedControl(selected: StatisticsPeriod, onSelect: (StatisticsPeriod) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.pillCornerRadius))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(4.dp)
    ) {
        SegmentButton(
            text = stringResource(R.string.statistics_week),
            selected = selected == StatisticsPeriod.WEEK,
            onClick = { onSelect(StatisticsPeriod.WEEK) }
        )
        SegmentButton(
            text = stringResource(R.string.statistics_month),
            selected = selected == StatisticsPeriod.MONTH,
            onClick = { onSelect(StatisticsPeriod.MONTH) }
        )
    }
}

@Composable
private fun SegmentButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(Dimens.pillCornerRadius))
            .background(if (selected) MaterialTheme.colorScheme.surfaceContainerLowest else androidx.compose.ui.graphics.Color.Transparent)
            .clickableNoRipple(onClick)
            .padding(vertical = Dimens.spaceXS),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = Dimens.textS,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}