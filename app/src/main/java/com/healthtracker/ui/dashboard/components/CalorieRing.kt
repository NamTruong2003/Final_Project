package com.healthtracker.ui.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens

@Composable
fun CalorieRingCard(
    goalCalories: Int,
    caloriesIn: Int,
    caloriesOut: Int,
    remainingCalories: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cardCornerRadiusLarge))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(Dimens.spaceXL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(Dimens.ringSize),
            contentAlignment = Alignment.Center
        ) {
            val trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            val eatenColor = MaterialTheme.colorScheme.primary
            val burnedColor = MaterialTheme.colorScheme.tertiary

            val eatenFraction = if (goalCalories > 0) (caloriesIn.toFloat() / goalCalories).coerceIn(0f, 1f) else 0f
            val burnedFraction = if (goalCalories > 0) (caloriesOut.toFloat() / goalCalories).coerceIn(0f, 1f) else 0f

            Canvas(modifier = Modifier.size(Dimens.ringSize)) {
                val stroke = Stroke(width = Dimens.ringStroke.toPx(), cap = StrokeCap.Round)
                val arcSize = Size(size.width - stroke.width, size.height - stroke.width)
                val topLeft = Offset(stroke.width / 2, stroke.width / 2)

                drawArc(
                    color = trackColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = stroke,
                    size = arcSize,
                    topLeft = topLeft
                )
                drawArc(
                    color = eatenColor,
                    startAngle = -90f,
                    sweepAngle = 360f * eatenFraction,
                    useCenter = false,
                    style = stroke,
                    size = arcSize,
                    topLeft = topLeft
                )
                drawArc(
                    color = burnedColor,
                    startAngle = -90f,
                    sweepAngle = -360f * burnedFraction,
                    useCenter = false,
                    style = stroke,
                    size = arcSize,
                    topLeft = topLeft
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${if (remainingCalories >= 0) remainingCalories else -remainingCalories}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.dashboard_kcal_remaining),
                    fontSize = Dimens.textS,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(Dimens.spaceM))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RingLabel(label = stringResource(R.string.dashboard_target), value = goalCalories, dotColor = null)
            RingLabel(label = stringResource(R.string.dashboard_eaten), value = caloriesIn, dotColor = MaterialTheme.colorScheme.primary)
            RingLabel(label = stringResource(R.string.dashboard_burned), value = caloriesOut, dotColor = MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Composable
private fun RingLabel(label: String, value: Int, dotColor: androidx.compose.ui.graphics.Color?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            dotColor?.let {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(50))
                        .background(it)
                )
                Spacer(Modifier.width(4.dp))
            }
            Text(
                text = label.uppercase(),
                fontSize = Dimens.textXS,
                color = dotColor ?: MaterialTheme.colorScheme.outline
            )
        }
        Text(
            text = "$value",
            fontSize = Dimens.textM,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}