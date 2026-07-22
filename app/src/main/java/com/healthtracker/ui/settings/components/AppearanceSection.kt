package com.healthtracker.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsSuggest
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
import androidx.compose.ui.unit.sp
import com.healthtracker.R
import com.healthtracker.model.ColorPalette
import com.healthtracker.model.FontSize
import com.healthtracker.model.ThemeBrightness
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun BrightnessSelector(selected: ThemeBrightness, onSelect: (ThemeBrightness) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceXS)) {
        BrightnessOption(
            icon = Icons.Filled.LightMode,
            label = stringResource(R.string.brightness_light),
            selected = selected == ThemeBrightness.LIGHT,
            onClick = { onSelect(ThemeBrightness.LIGHT) },
            modifier = Modifier.weight(1f)
        )
        BrightnessOption(
            icon = Icons.Filled.DarkMode,
            label = stringResource(R.string.brightness_dark),
            selected = selected == ThemeBrightness.DARK,
            onClick = { onSelect(ThemeBrightness.DARK) },
            modifier = Modifier.weight(1f)
        )
        BrightnessOption(
            icon = Icons.Filled.SettingsSuggest,
            label = stringResource(R.string.brightness_system),
            selected = selected == ThemeBrightness.SYSTEM,
            onClick = { onSelect(ThemeBrightness.SYSTEM) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun BrightnessOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(Dimens.radiusM)
            )
            .clickableNoRipple(onClick)
            .padding(vertical = Dimens.spaceS)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ColorPaletteSelector(selected: ColorPalette, onSelect: (ColorPalette) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
        ColorSwatch(color = Color(0xFF0F5238), selected = selected == ColorPalette.GREEN, onClick = { onSelect(ColorPalette.GREEN) })
        ColorSwatch(color = Color(0xFF1565C0), selected = selected == ColorPalette.BLUE, onClick = { onSelect(ColorPalette.BLUE) })
        ColorSwatch(color = Color(0xFF6A1B9A), selected = selected == ColorPalette.PURPLE, onClick = { onSelect(ColorPalette.PURPLE) })
    }
}

@Composable
private fun ColorSwatch(color: Color, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = CircleShape
            )
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White)
        }
    }
}

@Composable
fun FontSizeSelector(selected: FontSize, onSelect: (FontSize) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceXS)) {
        FontSizeOption(
            label = stringResource(R.string.font_size_small),
            previewSize = 12.sp,
            selected = selected == FontSize.SMALL,
            onClick = { onSelect(FontSize.SMALL) },
            modifier = Modifier.weight(1f)
        )
        FontSizeOption(
            label = stringResource(R.string.font_size_medium),
            previewSize = 16.sp,
            selected = selected == FontSize.MEDIUM,
            onClick = { onSelect(FontSize.MEDIUM) },
            modifier = Modifier.weight(1f)
        )
        FontSizeOption(
            label = stringResource(R.string.font_size_large),
            previewSize = 20.sp,
            selected = selected == FontSize.LARGE,
            onClick = { onSelect(FontSize.LARGE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FontSizeOption(
    label: String,
    previewSize: androidx.compose.ui.unit.TextUnit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(Dimens.radiusM)
            )
            .clickableNoRipple(onClick)
            .padding(vertical = Dimens.spaceS, horizontal = Dimens.spaceXS),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Aa",
            fontSize = previewSize,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}