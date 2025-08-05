package com.example.chientx_apero.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState())
    val state: StateFlow<LoginState> = _state
    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(passwordVisible = !it.passwordVisible)
                }
            }

            is LoginIntent.CheckPermission -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getDatabase(intent.context)
                    val auth = db.userDao().checkPermission(intent.username, intent.password)
                    if(auth != null) {
                        _state.update {
                            it.copy(
                                isLoginSuccess = true
                            )
                        }
                        AppCache.currentUser = auth
                        Log.d("User", AppCache.currentUser.toString())
                        sendEvent(LoginEvent.ShowLoginMessage("Login Success"))
                    } else {
                        sendEvent(LoginEvent.ShowLoginMessage("Wrong login information"))
                    }
                }
            }

            LoginIntent.ResetLoginSuccess -> {
                _state.update {
                    it.copy(
                        isLoginSuccess = false
                    )
                }
            }
        }
    }

    private fun sendEvent(event: LoginEvent){
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}