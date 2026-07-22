package com.healthtracker.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun LanguageToggle(currentLanguage: String, onLanguageChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.pillCornerRadius))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(4.dp)
    ) {
        LanguageOption(
            label = "Tiếng Việt",
            selected = currentLanguage == "vi",
            onClick = { onLanguageChange("vi") },
            modifier = Modifier.weight(1f)
        )
        LanguageOption(
            label = "English",
            selected = currentLanguage == "en",
            onClick = { onLanguageChange("en") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun LanguageOption(label: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.pillCornerRadius))
            .background(if (selected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent)
            .clickableNoRipple(onClick)
            .padding(vertical = Dimens.spaceS),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = Dimens.textS,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}