package com.healthtracker.ui.mealdiary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.model.Food
import com.healthtracker.ui.mealdiary.MealDiaryUiState
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.util.displayName
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodBottomSheet(
    uiState: MealDiaryUiState,
    onDismiss: () -> Unit,
    onSelectTab: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSelectFood: (Food) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCustomNameChange: (String) -> Unit,
    onCustomCaloriesChange: (String) -> Unit,
    onConfirm: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val configuration = LocalConfiguration.current
    val sheetHeight = configuration.screenHeightDp.dp * 0.7f
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .padding(horizontal = Dimens.spaceL)
                .padding(bottom = Dimens.spaceXL)
        ) {
            Text(
                text = "${stringResource(R.string.meal_add_food)} — ${uiState.addSheetMealType.displayName()}",
                fontSize = Dimens.textL,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.spaceM))

            TabRow(selectedTabIndex = if (uiState.isCustomTabSelected) 1 else 0) {
                Tab(
                    selected = !uiState.isCustomTabSelected,
                    onClick = { onSelectTab(false) },
                    text = { Text(stringResource(R.string.meal_tab_from_list)) }
                )
                Tab(
                    selected = uiState.isCustomTabSelected,
                    onClick = { onSelectTab(true) },
                    text = { Text(stringResource(R.string.meal_tab_custom)) }
                )
            }

            Spacer(Modifier.height(Dimens.spaceM))

            Box(modifier = Modifier.weight(1f)) {
                if (uiState.isCustomTabSelected) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        CustomFoodForm(
                            name = uiState.customFoodName,
                            calories = uiState.customFoodCalories,
                            onNameChange = onCustomNameChange,
                            onCaloriesChange = onCustomCaloriesChange
                        )
                    }
                } else {
                    FromListForm(
                        query = uiState.foodSearchQuery,
                        results = uiState.foodSearchResults,
                        selectedFood = uiState.selectedFood,
                        quantity = uiState.quantityInput,
                        onQueryChange = onSearchQueryChange,
                        onSelectFood = onSelectFood,
                        onQuantityChange = onQuantityChange
                    )
                }
            }

            Spacer(Modifier.height(Dimens.spaceS))

            val canConfirm = if (uiState.isCustomTabSelected) {
                uiState.customFoodName.isNotBlank() && (uiState.customFoodCalories.toIntOrNull()
                    ?: 0) > 0
            } else {
                uiState.selectedFood != null && (uiState.quantityInput.toIntOrNull() ?: 0) > 0
            }

            Button(
                onClick = onConfirm,
                enabled = canConfirm && !uiState.isSaving,
                shape = RoundedCornerShape(Dimens.radiusM),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(stringResource(R.string.common_add))
                }
            }

            Spacer(Modifier.height(Dimens.spaceM))
        }
    }
}


@Composable
private fun FromListForm(
    query: String,
    results: List<Food>,
    selectedFood: Food?,
    quantity: String,
    onQueryChange: (String) -> Unit,
    onSelectFood: (Food) -> Unit,
    onQuantityChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(stringResource(R.string.meal_search_placeholder)) },
            singleLine = true,
            shape = RoundedCornerShape(Dimens.radiusM),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(Dimens.spaceS))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(results) { food ->
                FoodResultRow(
                    food = food,
                    selected = selectedFood?.id == food.id,
                    onClick = { onSelectFood(food) }
                )
            }
        }

        if (selectedFood != null) {
            Spacer(Modifier.height(Dimens.spaceM))
            Text(
                text = stringResource(R.string.meal_quantity),
                fontSize = Dimens.textS,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value = quantity,
                onValueChange = onQuantityChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(Dimens.radiusM),
                suffix = { Text(selectedFood.unitLabel) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FoodResultRow(food: Food, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radiusS))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else androidx.compose.ui.graphics.Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = Dimens.spaceXS, horizontal = Dimens.spaceS),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = food.name, fontSize = Dimens.textS, color = MaterialTheme.colorScheme.onSurface)
        Text(
            text = "${food.caloriesPerUnit} kcal/${food.unitLabel}",
            fontSize = Dimens.textXS,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CustomFoodForm(
    name: String,
    calories: String,
    onNameChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text(stringResource(R.string.meal_custom_food_name)) },
        singleLine = true,
        shape = RoundedCornerShape(Dimens.radiusM),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(Dimens.spaceM))
    OutlinedTextField(
        value = calories,
        onValueChange = onCaloriesChange,
        label = { Text(stringResource(R.string.meal_custom_food_calories)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(Dimens.radiusM),
        modifier = Modifier.fillMaxWidth()
    )
}