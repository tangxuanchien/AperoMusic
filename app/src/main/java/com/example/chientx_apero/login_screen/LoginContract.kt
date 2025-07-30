package com.example.chientx_apero.login_screen

import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class LoginState(
    val username: String = "",
    val password: String = "",
    val usernameError: String = "",
    val passwordError: String = "",
    val passwordVisible: Boolean = false,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false
)

sealed interface LoginIntent {
    data class UsernameChanged(val username: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data class SetInitialData(val username: String, val password: String) : LoginIntent
    data object TogglePasswordVisibility : LoginIntent
}

sealed interface MviEvent {
//    Show message error
}