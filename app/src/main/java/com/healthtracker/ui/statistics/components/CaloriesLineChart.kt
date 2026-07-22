package com.healthtracker.ui.statistics.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthtracker.ui.statistics.ChartPoint
import com.healthtracker.ui.theme.Dimens
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CaloriesLineChart(stats: List<ChartPoint>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.chartHeight)
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.08f))
            .padding(Dimens.spaceM)
    ) {
        val maxValue = (stats.maxOfOrNull { it.caloriesOut } ?: 1).coerceAtLeast(1)
        val lineColor = MaterialTheme.colorScheme.tertiary
        val pointCenterColor = MaterialTheme.colorScheme.surfaceContainerLowest

        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (stats.size < 2) return@Canvas

            val stepX = size.width / (stats.size - 1)
            val points = stats.mapIndexed { index, stat ->
                val x = index * stepX
                val y = size.height - (stat.caloriesOut.toFloat() / maxValue) * size.height
                Offset(x, y)
            }

            val linePath = Path().apply {
                moveTo(points.first().x, points.first().y)
                for (i in 1 until points.size) {
                    lineTo(points[i].x, points[i].y)
                }
            }

            val fillPath = Path().apply {
                addPath(linePath)
                lineTo(points.last().x, size.height)
                lineTo(points.first().x, size.height)
                close()
            }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(lineColor.copy(alpha = 0.25f), lineColor.copy(alpha = 0f))
                )
            )
            drawPath(
                path = linePath,
                color = lineColor,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx())
            )
            points.forEach { point ->
                drawCircle(color = lineColor, radius = 4.dp.toPx(), center = point)
                drawCircle(
                    color = pointCenterColor,
                    radius = 2.dp.toPx(),
                    center = point
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stats.forEach { stat ->
                Text(
                    text = stat.displayLabel(),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}