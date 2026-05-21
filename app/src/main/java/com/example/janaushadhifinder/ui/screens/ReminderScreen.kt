package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Notifications
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.navigation.Routes
import com.example.janaushadhifinder.ui.components.AppHeader
import com.example.janaushadhifinder.ui.components.BottomDashboardBar
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun ReminderScreen(navController: NavHostController) {
    var medicine by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    val reminders = remember {
        mutableStateListOf(
            "Pan 40 refill in 12 days",
            "Telma 40 refill in 24 days"
        )
    }

    Scaffold(
        bottomBar = { BottomDashboardBar(navController, Routes.Reminders) },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(AppColors.Background)
        ) {
            AppHeader(
                title = "Refill Reminders",
                subtitle = "Monthly medicine tracking",
                showBack = true,
                onBack = { navController.popBackStack() }
            )

            Column(Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = medicine,
                    onValueChange = { medicine = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Medicine name") },
                    leadingIcon = { Icon(Icons.Rounded.Notifications, contentDescription = null) },
                    singleLine = true
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = days,
                    onValueChange = { days = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Refill after how many days?") },
                    leadingIcon = { Icon(Icons.Rounded.DateRange, contentDescription = null) },
                    singleLine = true
                )

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (medicine.isNotBlank() && days.isNotBlank()) {
                            reminders.add("$medicine refill in $days days")
                            medicine = ""
                            days = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryBlue)
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = null)
                    Text("Add Reminder", fontWeight = FontWeight.Bold)
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(reminders) { reminder ->
                    Card(colors = CardDefaults.cardColors(containerColor = AppColors.Surface)) {
                        Text(
                            reminder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = AppColors.TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
