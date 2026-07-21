package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens

@Composable
fun QuickActionButtons(onAddMealClick: () -> Unit, onAddActivityClick: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spaceM)) {
        Button(
            onClick = onAddMealClick,
            shape = RoundedCornerShape(Dimens.radiusM),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(Modifier.width(Dimens.spaceXXS))
            Text(stringResource(R.string.dashboard_add_meal))
        }
        OutlinedButton(
            onClick = onAddActivityClick,
            shape = RoundedCornerShape(Dimens.radiusM),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
            border = androidx.compose.foundation.BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(Modifier.width(Dimens.spaceXXS))
            Text(stringResource(R.string.dashboard_add_activity))
        }
    }
}