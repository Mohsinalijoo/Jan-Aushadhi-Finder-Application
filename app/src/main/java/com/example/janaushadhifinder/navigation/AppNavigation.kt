package com.example.janaushadhifinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.janaushadhifinder.data.AuthStore
import com.example.janaushadhifinder.ui.screens.DashboardScreen
import com.example.janaushadhifinder.ui.screens.LoginScreen
import com.example.janaushadhifinder.ui.screens.MedicineSearchScreen
import com.example.janaushadhifinder.ui.screens.ReminderScreen
import com.example.janaushadhifinder.ui.screens.SignupScreen
import com.example.janaushadhifinder.ui.screens.StockRequestScreen
import com.example.janaushadhifinder.ui.screens.StoreLocatorScreen

object Routes {
    const val Login = "login"
    const val Signup = "signup"
    const val Dashboard = "dashboard"
    const val Search = "search"
    const val Stores = "stores"
    const val Reminders = "reminders"
    const val Stock = "stock"
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val authStore = remember { AuthStore(context.applicationContext) }

    NavHost(navController = navController, startDestination = Routes.Login) {
        composable(Routes.Login) {
            LoginScreen(
                onLogin = authStore::login,
                onLoginSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onSignupClick = { navController.navigate(Routes.Signup) }
            )
        }
        composable(Routes.Signup) {
            SignupScreen(
                onSignup = authStore::signup,
                onSignupSuccess = {
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                },
                onLoginClick = { navController.popBackStack() }
            )
        }
        composable(Routes.Dashboard) {
            DashboardScreen(navController = navController)
        }
        composable(Routes.Search) {
            MedicineSearchScreen(navController = navController)
        }
        composable(Routes.Stores) {
            StoreLocatorScreen(navController = navController)
        }
        composable(Routes.Reminders) {
            ReminderScreen(navController = navController)
        }
        composable(Routes.Stock) {
            StockRequestScreen(navController = navController)
        }
    }
}

fun NavHostController.goHome() {
    navigate(Routes.Dashboard) {
        popUpTo(Routes.Dashboard) { inclusive = true }
        launchSingleTop = true
    }
}
