package com.healthtracker.ui.statistics.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthtracker.R
import com.healthtracker.ui.statistics.ChartPoint
import com.healthtracker.ui.theme.Dimens


@Composable
fun CaloriesBarChart(stats: List<ChartPoint>, goalCalories: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.chartHeight)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f))
            .padding(Dimens.spaceM)
    ) {
        Text(
            text = stringResource(R.string.statistics_goal_label, goalCalories),
            fontSize = Dimens.textXS,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(Modifier.height(Dimens.spaceXS))

        val maxValue =
            (stats.maxOfOrNull { it.caloriesIn } ?: 1).coerceAtLeast(goalCalories).coerceAtLeast(1)

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            stats.forEach { stat ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val fraction = (stat.caloriesIn.toFloat() / maxValue).coerceIn(0f, 1f)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(fraction)
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stat.displayLabel(),
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

