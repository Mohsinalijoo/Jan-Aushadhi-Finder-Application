package com.example.janaushadhifinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.janaushadhifinder.data.Medicine
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun MedicineCard(medicine: Medicine, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = AppColors.HealthyGreen)
                Column(Modifier.padding(start = 10.dp)) {
                    Text(medicine.brandName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(medicine.genericName, color = AppColors.HealthyGreen, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(10.dp))
            Text("Salt: ${medicine.salt}", color = AppColors.TextMuted, fontSize = 13.sp)

            Spacer(Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Branded", color = AppColors.TextMuted, fontSize = 12.sp)
                    Text(
                        text = "\u20B9${medicine.brandPrice}",
                        color = AppColors.Danger,
                        textDecoration = TextDecoration.LineThrough,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Jan-Aushadhi", color = AppColors.TextMuted, fontSize = 12.sp)
                    Text(
                        text = "\u20B9${medicine.genericPrice}",
                        color = AppColors.HealthyGreen,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { medicine.savingsPercent / 100f },
                modifier = Modifier.fillMaxWidth(),
                color = AppColors.HealthyGreen,
                trackColor = AppColors.SoftBlue
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "You save \u20B9${medicine.savings} (${medicine.savingsPercent}%)",
                color = AppColors.PrimaryBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
