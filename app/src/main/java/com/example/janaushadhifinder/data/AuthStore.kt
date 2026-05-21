package com.example.janaushadhifinder.data

import android.content.Context

class AuthStore(context: Context) {
    private val preferences = context.getSharedPreferences("jan_aushadhi_auth", Context.MODE_PRIVATE)

    fun signup(fullName: String, mobile: String, password: String): AuthResult {
        val cleanName = fullName.trim()
        val cleanMobile = mobile.trim()

        if (cleanName.isBlank() || cleanMobile.isBlank() || password.isBlank()) {
            return AuthResult.Error("Please fill all fields.")
        }
        if (cleanMobile.length < 10) {
            return AuthResult.Error("Enter a valid mobile number.")
        }
        if (password.length < 4) {
            return AuthResult.Error("Password must be at least 4 characters.")
        }
        if (preferences.getString(KEY_MOBILE, null) == cleanMobile) {
            return AuthResult.Error("This mobile number is already registered.")
        }

        preferences.edit()
            .putString(KEY_NAME, cleanName)
            .putString(KEY_MOBILE, cleanMobile)
            .putString(KEY_PASSWORD, password)
            .apply()

        return AuthResult.Success
    }

    fun login(mobile: String, password: String): AuthResult {
        val savedMobile = preferences.getString(KEY_MOBILE, null)
        val savedPassword = preferences.getString(KEY_PASSWORD, null)

        if (savedMobile == null || savedPassword == null) {
            return AuthResult.Error("Create an account first.")
        }

        return if (savedMobile == mobile.trim() && savedPassword == password) {
            AuthResult.Success
        } else {
            AuthResult.Error("Invalid mobile number or password.")
        }
    }

    private companion object {
        const val KEY_NAME = "full_name"
        const val KEY_MOBILE = "mobile"
        const val KEY_PASSWORD = "password"
    }
}

sealed interface AuthResult {
    data object Success : AuthResult
    data class Error(val message: String) : AuthResult
}
