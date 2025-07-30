package com.example.chientx_apero.signup_screen

import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val usernameError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = "",
    val emailError: String = "",
    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false,
    val isSubmitSignUp: Boolean = false
){

}

sealed interface SignUpIntent {
    data class UsernameChanged(val username: String) : SignUpIntent
    data class PasswordChanged(val password: String) : SignUpIntent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpIntent
    data class EmailChanged(val email: String) : SignUpIntent
    data class SetInitialData(val username: String, val password: String) : SignUpIntent
    data object TogglePasswordVisibility : SignUpIntent
    data object ToggleConfirmPasswordVisibility : SignUpIntent
    data object SubmitSignUp : SignUpIntent
    data object ResetIsSubmitSignUp : SignUpIntent
}

sealed interface MviEvent {
//    Show message error
}