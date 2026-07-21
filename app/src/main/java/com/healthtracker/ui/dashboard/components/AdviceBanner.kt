package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.util.AdviceResult

@Composable
fun AdviceBanner(advice: AdviceResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardCornerRadiusSmall))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(Dimens.spaceM)
    ) {
        Icon(
            Icons.Filled.TipsAndUpdates,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 2.dp)
        )
        Spacer(Modifier.width(Dimens.spaceS))

        val kcalUnit = stringResource(R.string.dashboard_kcal_unit)
        val text = when (advice) {
            is AdviceResult.UnderGoal -> buildAnnotatedString {
                append(stringResource(R.string.advice_under_goal_prefix))
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)) {
                    append(" ${advice.remainingKcal} $kcalUnit ")
                }
                append(stringResource(R.string.advice_under_goal_suffix))
            }
            is AdviceResult.OnTrack -> buildAnnotatedString {
                append(stringResource(R.string.advice_on_track))
            }
            is AdviceResult.OverGoal -> buildAnnotatedString {
                append(stringResource(R.string.advice_over_goal_prefix))
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)) {
                    append(" ${advice.overKcal} $kcalUnit ")
                }
                append(stringResource(R.string.advice_over_goal_suffix))
            }
        }

        Text(text = text, fontSize = Dimens.textS, color = MaterialTheme.colorScheme.onSurface)
    }
}