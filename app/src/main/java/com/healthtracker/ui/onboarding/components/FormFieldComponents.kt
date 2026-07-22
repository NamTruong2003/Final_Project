package com.healthtracker.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun LabeledField(label: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = label.uppercase(),
            fontSize = Dimens.textXS,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 0.5.sp
        )
        Spacer(Modifier.height(Dimens.spaceXXS + 2.dp)
        )
        content()
    }
}

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        fontSize = Dimens.textXS,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(top = Dimens.spaceXXS)
    )
}

@Composable
fun DesignTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) },
        singleLine = true,
        shape = RoundedCornerShape(Dimens.radiusM),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DateField(dateText: String, placeholder: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.fieldHeight)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(Dimens.borderThin, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(Dimens.radiusM))
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = dateText.ifBlank { placeholder },
            color = if (dateText.isBlank())
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.onSurface,
            fontSize = Dimens.textM,
            modifier = Modifier.padding(start = Dimens.spaceM)
        )
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = Dimens.spaceS)
        )
    }
}