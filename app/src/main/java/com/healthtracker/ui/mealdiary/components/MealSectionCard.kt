package com.healthtracker.ui.mealdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.model.MealType
import com.healthtracker.model.MealWithFoodInfo
import com.healthtracker.ui.components.clickableNoRipple
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.util.displayName
import com.healthtracker.util.icon

@Composable
fun MealSectionCard(
    mealType: MealType,
    items: List<MealWithFoodInfo>,
    totalCalories: Int,
    onAddClick: () -> Unit,
    onDeleteItem: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radiusL))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
                .padding(Dimens.spaceM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(Dimens.mealIconSize)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(mealType.icon(), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                Spacer(Modifier.width(Dimens.spaceS))
                Column {
                    Text(
                        text = mealType.displayName(),
                        fontSize = Dimens.textM,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$totalCalories ${stringResource(R.string.dashboard_kcal_unit)}",
                        fontSize = Dimens.textS,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickableNoRipple(onAddClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }

        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.meal_no_items),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(Dimens.spaceM)
            )
        } else {
            Column(
                modifier = Modifier.padding(Dimens.spaceM),
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceS)
            ) {
                items.forEachIndexed { index, item ->
                    MealItemRow(item = item, onDelete = { onDeleteItem(item.id) })
                    if (index != items.lastIndex) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    }
                }
            }
        }
    }
}

@Composable
private fun MealItemRow(item: MealWithFoodInfo, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.displayName,
                fontSize = Dimens.textM,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (item.unitLabel.isNotBlank()) "${item.quantity} ${item.unitLabel}" else "x${item.quantity}",
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${item.totalCalories} ",
                fontSize = Dimens.textM,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.dashboard_kcal_unit),
                fontSize = Dimens.textXS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(Dimens.spaceXS))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickableNoRipple(onDelete),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}