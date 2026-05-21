package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.data.SampleData
import com.example.janaushadhifinder.navigation.Routes
import com.example.janaushadhifinder.ui.components.AppHeader
import com.example.janaushadhifinder.ui.components.BottomDashboardBar
import com.example.janaushadhifinder.ui.components.MedicineCard
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomDashboardBar(navController, Routes.Dashboard) },
        containerColor = AppColors.Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                AppHeader()
            }

            item {
                DashboardHero(
                    onCompare = { navController.navigate(Routes.Search) },
                    onLocate = { navController.navigate(Routes.Stores) }
                )
            }

            item {
                SavingsMilestone()
            }

            item {
                SectionTitle("Popular generic switches")
            }

            items(SampleData.popularSwitches) { medicine ->
                MedicineCard(
                    medicine = medicine,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                SectionTitle("Tools")
            }

            item {
                FeatureCard(
                    title = "Savings Tracker",
                    body = "Calculate annual savings when switching to generics.",
                    icon = Icons.Rounded.CheckCircle,
                    onClick = { navController.navigate(Routes.Search) }
                )
            }

            item {
                FeatureCard(
                    title = "Store Locator",
                    body = "Find Jan-Aushadhi Kendras within 10 km.",
                    icon = Icons.Rounded.LocationOn,
                    onClick = { navController.navigate(Routes.Stores) }
                )
            }

            item {
                FeatureCard(
                    title = "Stock Request",
                    body = "Ask a store if your medicine is available.",
                    icon = Icons.Rounded.Send,
                    onClick = { navController.navigate(Routes.Stock) }
                )
            }

            item {
                FeatureCard(
                    title = "Refill Reminders",
                    body = "Track monthly medicine refills.",
                    icon = Icons.Rounded.Notifications,
                    onClick = { navController.navigate(Routes.Reminders) }
                )
                Spacer(Modifier.height(18.dp))
            }
        }
    }
}

@Composable
private fun DashboardHero(onCompare: () -> Unit, onLocate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(AppColors.Navy, AppColors.NavySoft)
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text("SMART INDIA HEALTHCARE", color = Color(0xFF79A0FF), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(18.dp))
                Text(
                    "Save up to 90% on your medicine bills.",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 38.sp
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Find generic salts at certified Jan-Aushadhi Kendras.",
                    color = Color(0xFFAAB4C8),
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )
                Spacer(Modifier.height(24.dp))
                Row {
                    Button(
                        onClick = onCompare,
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryBlue)
                    ) {
                        Icon(Icons.Rounded.Search, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Compare")
                    }
                    Spacer(Modifier.width(10.dp))
                    OutlinedButton(onClick = onLocate) {
                        Text("Locate")
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Rounded.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Composable
private fun SavingsMilestone() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.DeepBlue)
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ANNUAL SAVINGS MILESTONE", color = Color(0xFFC3CEFF), fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(8.dp))
            Text("Level 4 Saver", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(6.dp))
            Text("Total savings: \u20B912,450", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun FeatureCard(title: String, body: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = AppColors.PrimaryBlue)
            Spacer(Modifier.width(14.dp))
            Column {
                Text(title, color = AppColors.TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(body, color = AppColors.TextMuted, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
        color = AppColors.TextPrimary,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp
    )
}
