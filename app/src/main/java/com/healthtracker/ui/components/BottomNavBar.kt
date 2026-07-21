package com.healthtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens

enum class BottomNavTab { HOME, DIARY, STATS, PROFILE }

@Composable
fun BottomNavBar(currentTab: BottomNavTab, onTabSelected: (BottomNavTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .height(Dimens.bottomNavHeight)
            .padding(horizontal = Dimens.spaceM),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.nav_home),
            selected = currentTab == BottomNavTab.HOME,
            onClick = { onTabSelected(BottomNavTab.HOME) }
        )
        BottomNavItem(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            label = stringResource(R.string.nav_diary),
            selected = currentTab == BottomNavTab.DIARY,
            onClick = { onTabSelected(BottomNavTab.DIARY) }
        )
        BottomNavItem(
            icon = Icons.Filled.BarChart,
            label = stringResource(R.string.nav_stats),
            selected = currentTab == BottomNavTab.STATS,
            onClick = { onTabSelected(BottomNavTab.STATS) }
        )
        BottomNavItem(
            icon = Icons.Filled.Person,
            label = stringResource(R.string.nav_profile),
            selected = currentTab == BottomNavTab.PROFILE,
            onClick = { onTabSelected(BottomNavTab.PROFILE) }
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer else androidx.compose.ui.graphics.Color.Transparent)
            .clickableNoRipple(onClick)
            .padding(horizontal = Dimens.spaceM, vertical = Dimens.spaceXXS)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = label,
            fontSize = Dimens.textXS,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}