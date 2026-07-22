package com.healthtracker.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.healthtracker.R
import com.healthtracker.ui.components.BottomNavBar
import com.healthtracker.ui.components.BottomNavTab
import com.healthtracker.ui.settings.components.*
import com.healthtracker.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit,
    onTabSelected: (BottomNavTab) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        bottomBar = {
            BottomNavBar(currentTab = BottomNavTab.PROFILE, onTabSelected = onTabSelected)
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimens.spaceL),
                verticalArrangement = Arrangement.spacedBy(Dimens.spaceL)
            ) {
                Spacer(Modifier.height(Dimens.spaceS))

                ProfileSummaryCard(
                    userName = uiState.userName,
                    bmi = uiState.bmi,
                    bmiCategory = uiState.bmiCategory,
                    onClick = onEditProfileClick
                )

                SettingsCard(title = stringResource(R.string.settings_language)) {
                    LanguageToggle(
                        currentLanguage = uiState.language,
                        onLanguageChange = viewModel::onLanguageChange
                    )
                }

                SettingsCard(title = stringResource(R.string.settings_appearance)) {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimens.spaceL)) {
                        Column {
                            Text(
                                text = stringResource(R.string.settings_brightness),
                                fontSize = Dimens.textS,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(Dimens.spaceS))
                            BrightnessSelector(selected = uiState.themeBrightness, onSelect = viewModel::onBrightnessChange)
                        }
                        Column {
                            Text(
                                text = stringResource(R.string.settings_color),
                                fontSize = Dimens.textS,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(Dimens.spaceS))
                            ColorPaletteSelector(selected = uiState.colorPalette, onSelect = viewModel::onColorPaletteChange)
                        }
                        Column {
                            Text(
                                text = stringResource(R.string.settings_font_size),
                                fontSize = Dimens.textS,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(Dimens.spaceS))
                            FontSizeSelector(selected = uiState.fontSize, onSelect = viewModel::onFontSizeChange)
                        }
                    }
                }

                SettingsCard(title = stringResource(R.string.settings_other)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.settings_app_version),
                            fontSize = Dimens.textM,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "v${uiState.appVersion}",
                            fontSize = Dimens.textS,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.height(Dimens.spaceL))
            }
        }
    }
}

@Composable
private fun SettingsCard(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardCornerRadiusLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(Dimens.spaceL)
    ) {
        Text(
            text = title,
            fontSize = Dimens.textL,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(Dimens.spaceM))
        content()
    }
}