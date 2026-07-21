package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Balance
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
import com.healthtracker.ui.theme.Dimens
import androidx.compose.ui.draw.drawBehind

@Composable
fun BalanceCard(netCalories: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardCornerRadiusMedium))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .drawBehind {
                drawRect(
                    color = androidx.compose.ui.graphics.Color(0xFF00696C),
                    size = androidx.compose.ui.geometry.Size(4.dp.toPx(), size.height)
                )
            }
            .padding(Dimens.spaceL),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.dashboard_net_balance),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = if (netCalories >= 0) "+$netCalories" else "$netCalories",
                    fontSize = Dimens.textL,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.dashboard_kcal_unit),
                    fontSize = Dimens.textXS,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Balance, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}