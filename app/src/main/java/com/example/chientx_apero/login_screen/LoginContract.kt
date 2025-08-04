package com.example.chientx_apero.login_screen

import android.content.Context
import android.os.Message
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class LoginState(
    val passwordVisible: Boolean = false,
    val checked: Boolean = false,
    var isLoginSuccess: Boolean = false
){
    val visualTransformation: VisualTransformation = if (!passwordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}

sealed interface LoginIntent {
    data object TogglePasswordVisibility : LoginIntent
    data object ResetLoginSuccess : LoginIntent
    data class CheckPermission(val username: String, val password: String, val context: Context) : LoginIntent
}

sealed interface LoginEvent {
    data class ShowLoginMessage(val message: String) : LoginEvent
}