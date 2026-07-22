package com.healthtracker.ui.settings.editprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.healthtracker.R
import com.healthtracker.ui.theme.Dimens
import com.healthtracker.util.classifyBmi
import com.healthtracker.util.displayName

@Composable
fun TdeeBmiSummaryCard(tdee: Int?, bmi: Double?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.radiusM))
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f))
            .padding(Dimens.spaceL)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.LocalFireDepartment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(Dimens.spaceXS))
                Text(
                    text = stringResource(R.string.tdee_goal_label),
                    fontSize = Dimens.textS,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = if (tdee != null) "$tdee ${stringResource(R.string.dashboard_kcal_unit)}" else "—",
                fontSize = Dimens.textL,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            modifier = Modifier.padding(vertical = Dimens.spaceS)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Straighten, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(Dimens.spaceXS))
                Text(
                    text = stringResource(R.string.bmi_index_label),
                    fontSize = Dimens.textS,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (bmi != null) "%.1f".format(bmi) else "—",
                    fontSize = Dimens.textL,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (bmi != null) {
                    Spacer(Modifier.width(Dimens.spaceXS))
                    androidx.compose.material3.Surface(
                        shape = RoundedCornerShape(Dimens.radiusS),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = classifyBmi(bmi).displayName(),
                            fontSize = Dimens.textXS,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = Dimens.spaceXS, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}