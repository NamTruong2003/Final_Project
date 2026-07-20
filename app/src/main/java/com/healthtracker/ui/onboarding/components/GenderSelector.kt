package com.healthtracker.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.healthtracker.model.Gender
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun GenderSegmentedControl(selected: Gender?, onSelect: (Gender) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.fieldHeight)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(4.dp)
    ) {
        GenderSegmentButton(
            text = stringResource(R.string.gender_male),
            selected = selected == Gender.MALE,
            onClick = { onSelect(Gender.MALE) },
            modifier = Modifier.weight(1f)
        )
        GenderSegmentButton(
            text = stringResource(R.string.gender_female),
            selected = selected == Gender.FEMALE,
            onClick = { onSelect(Gender.FEMALE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GenderSegmentButton(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(Dimens.radiusS))
            .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = Dimens.textS,
            fontWeight = FontWeight.Medium
        )
    }
}