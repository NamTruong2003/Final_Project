package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
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
import com.healthtracker.ui.theme.Dimens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateSelector(date: LocalDate, onPreviousDay: () -> Unit, onNextDay: () -> Unit) {
    val label = if (date == LocalDate.now())
        stringResource(R.string.dashboard_today)
    else
        date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimens.pillCornerRadius))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(Dimens.spaceXXS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(50)).clickableNoRipple(onPreviousDay),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }

        Row(
            modifier = Modifier.padding(horizontal = Dimens.spaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = Dimens.textM, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.width(Dimens.spaceXXS))
            Icon(
                Icons.Filled.CalendarToday,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(16.dp)
            )
        }

        Box(
            modifier = Modifier.size(32.dp).clip(RoundedCornerShape(50)).clickableNoRipple(onNextDay),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}