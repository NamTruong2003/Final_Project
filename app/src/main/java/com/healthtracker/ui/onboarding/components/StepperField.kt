package com.healthtracker.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens

@Composable
fun StepperField(value: String, onValueChange: (String) -> Unit, step: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.fieldHeight)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(Dimens.borderThin, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(Dimens.radiusM)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StepperButton(
            icon = Icons.Filled.Remove,
            onClick = {
                val current = value.toDoubleOrNull() ?: 0.0
                onValueChange(formatStepperValue(current - step))
            }
        )
        Box(modifier = Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
            BasicNumberText(
                value = value,
                onValueChange = { input -> if (isValidNumberInput(input)) onValueChange(input) }
            )
        }
        StepperButton(
            icon = Icons.Filled.Add,
            onClick = {
                val current = value.toDoubleOrNull() ?: 0.0
                onValueChange(formatStepperValue(current + step))
            }
        )
    }
}

@Composable
private fun StepperButton(icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier.width(Dimens.stepperButtonWidth).fillMaxHeight().clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun BasicNumberText(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = Dimens.textL,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

private fun isValidNumberInput(input: String): Boolean {
    if (input.isEmpty()) return true
    return input.matches(Regex("^\\d{0,3}(\\.\\d{0,2})?$"))
}

private fun formatStepperValue(v: Double): String {
    val clamped = if (v < 0) 0.0 else v
    return if (clamped == clamped.toInt().toDouble()) clamped.toInt().toString() else clamped.toString()
}