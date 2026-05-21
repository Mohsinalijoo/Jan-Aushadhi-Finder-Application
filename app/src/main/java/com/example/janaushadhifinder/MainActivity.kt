package com.example.janaushadhifinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.janaushadhifinder.navigation.AppNavigation
import com.example.janaushadhifinder.ui.theme.JanAushadhiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JanAushadhiTheme {
                AppNavigation()
            }
        }
    }
}
