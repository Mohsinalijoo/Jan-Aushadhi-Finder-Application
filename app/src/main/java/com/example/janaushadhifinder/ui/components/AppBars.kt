package com.example.janaushadhifinder.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.navigation.Routes
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun AppHeader(
    title: String = "Jan-Aushadhi Finder",
    subtitle: String = "Official Healthcare Savings Tool",
    showBack: Boolean = false,
    onBack: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBack) {
            IconButton(onClick = onBack) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        } else {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(44.dp)
                    .medicalLogoBackground()
                    .padding(10.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(title, color = AppColors.TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text(subtitle, color = AppColors.TextMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BottomDashboardBar(navController: NavHostController, selectedRoute: String) {
    NavigationBar(containerColor = Color.White) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

private data class BottomItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

private val bottomItems = listOf(
    BottomItem("Home", Routes.Dashboard, Icons.Rounded.Home),
    BottomItem("Search", Routes.Search, Icons.Rounded.Search),
    BottomItem("Stores", Routes.Stores, Icons.Rounded.LocationOn),
    BottomItem("Refills", Routes.Reminders, Icons.Rounded.Notifications)
)

@Composable
fun ScreenSpacer() {
    Spacer(Modifier.height(12.dp))
}
