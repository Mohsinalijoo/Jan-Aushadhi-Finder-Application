package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.janaushadhifinder.data.AuthResult
import com.example.janaushadhifinder.ui.components.medicalLogoBackground
import com.example.janaushadhifinder.ui.theme.AppColors

@Composable
fun LoginScreen(
    onLogin: (String, String) -> AuthResult,
    onLoginSuccess: () -> Unit,
    onSignupClick: () -> Unit
) {
    var mobile by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Favorite,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(64.dp)
                .medicalLogoBackground()
                .padding(14.dp)
        )

        Spacer(Modifier.height(18.dp))

        Text("Jan-Aushadhi Finder", color = AppColors.TextPrimary, fontSize = 31.sp, fontWeight = FontWeight.ExtraBold)
        Text("Healthcare savings tool", color = AppColors.TextMuted, fontWeight = FontWeight.SemiBold)

        Spacer(Modifier.height(30.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Mobile number") },
            leadingIcon = { Icon(Icons.Rounded.Phone, contentDescription = null) },
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        if (error.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(error, color = AppColors.Danger, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                when (val result = onLogin(mobile, password)) {
                    AuthResult.Success -> onLoginSuccess()
                    is AuthResult.Error -> error = result.message
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryBlue)
        ) {
            Text("Login", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(onClick = onSignupClick) {
                Text("Create new account")
            }
        }
    }
}
