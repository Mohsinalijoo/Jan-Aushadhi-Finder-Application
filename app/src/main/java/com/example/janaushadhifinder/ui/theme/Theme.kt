package com.example.janaushadhifinder.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val Background = Color(0xFFF5F7FB)
    val Surface = Color.White
    val Navy = Color(0xFF101A33)
    val NavySoft = Color(0xFF172341)
    val PrimaryBlue = Color(0xFF3867E8)
    val SoftBlue = Color(0xFFEAF0FF)
    val DeepBlue = Color(0xFF2E4596)
    val HealthyGreen = Color(0xFF47A36D)
    val TextPrimary = Color(0xFF202A44)
    val TextMuted = Color(0xFF76839A)
    val Danger = Color(0xFFD94A4A)
}

private val LightScheme = lightColorScheme(
    primary = AppColors.PrimaryBlue,
    secondary = AppColors.HealthyGreen,
    background = AppColors.Background,
    surface = AppColors.Surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = AppColors.TextPrimary,
    onSurface = AppColors.TextPrimary
)

@Composable
fun JanAushadhiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = Typography(),
        content = content
    )
}
