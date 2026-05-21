package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.data.SampleData
import com.example.janaushadhifinder.ui.components.AppHeader
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun StockRequestScreen(navController: NavHostController) {
    var medicine by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }
    val store = SampleData.stores.first()

    Scaffold(containerColor = AppColors.Background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(AppColors.Background)
        ) {
            AppHeader(
                title = "Stock Request",
                subtitle = "Simulated availability check",
                showBack = true,
                onBack = { navController.popBackStack() }
            )

            Column(Modifier.padding(16.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = AppColors.Surface)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Selected store", color = AppColors.TextMuted, fontWeight = FontWeight.Bold)
                        Text(store.name, color = AppColors.TextPrimary, fontWeight = FontWeight.ExtraBold)
                        Text(store.address, color = AppColors.TextMuted)
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = medicine,
                    onValueChange = { medicine = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Medicine name") },
                    leadingIcon = { Icon(Icons.Rounded.Send, contentDescription = null) },
                    singleLine = true
                )

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = {
                        confirmation = if (medicine.isBlank()) {
                            "Enter a medicine name first."
                        } else {
                            "Request sent to ${store.name}. Expected reply: In stock today after 5 PM."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryBlue)
                ) {
                    Text("Ask Store", fontWeight = FontWeight.Bold)
                }

                if (confirmation.isNotBlank()) {
                    Spacer(Modifier.height(18.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = AppColors.SoftBlue)) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = AppColors.HealthyGreen)
                            Spacer(Modifier.height(8.dp))
                            Text(confirmation, color = AppColors.TextPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
