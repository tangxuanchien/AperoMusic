package com.example.chientx_apero.login_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state

    fun processIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.UsernameChanged -> {
                _state.update {
                    it.copy(username = intent.username)
                }
            }
            is LoginIntent.PasswordChanged -> {
                _state.update {
                    it.copy(password = intent.password)
                }
            }
            is LoginIntent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(passwordVisible = !it.passwordVisible)
                }
            }
            is LoginIntent.SetInitialData -> {
                _state.update {
                    it.copy(
                        username = intent.username,
                        password = intent.password
                    )
                }
            }
        }
    }
}