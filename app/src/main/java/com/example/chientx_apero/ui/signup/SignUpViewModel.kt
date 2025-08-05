package com.example.chientx_apero.ui.signup


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.room_db.entity.User
import com.example.chientx_apero.room_db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _state = MutableStateFlow<SignUpState>(SignUpState())
    val state: StateFlow<SignUpState> = _state
    private val _event = MutableSharedFlow<SignUpEvent>()
    val event: SharedFlow<SignUpEvent> = _event.asSharedFlow()
    var db: AppDatabase? = null

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UsernameChanged -> {
                _state.update {
                    it.copy(username = intent.username, usernameError = "")
                }
            }

            is SignUpIntent.PasswordChanged -> {
                _state.update {
                    it.copy(password = intent.password, passwordError = "")
                }
            }

            is SignUpIntent.EmailChanged -> {
                _state.update {
                    it.copy(email = intent.email, emailError = "")
                }
            }

            is SignUpIntent.ConfirmPasswordChanged -> {
                _state.update {
                    it.copy(confirmPassword = intent.confirmPassword, confirmPasswordError = "")
                }
            }

            is SignUpIntent.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(passwordVisible = !it.passwordVisible)
                }
            }

            is SignUpIntent.ToggleConfirmPasswordVisibility -> {
                _state.update {
                    it.copy(confirmPasswordVisible = !it.confirmPasswordVisible)
                }
            }

            is SignUpIntent.SetInitialData -> {
                _state.update {
                    it.copy(
                        username = intent.username,
                        password = intent.password
                    )
                }
            }

            is SignUpIntent.SubmitSignUp -> {
                submitSignUp()
            }

            is SignUpIntent.ResetIsSubmitSignUp -> {
                _state.update {
                    it.copy(
                        isSubmitSignUp = false
                    )
                }
            }

            is SignUpIntent.ProvideContext -> {
                db = AppDatabase.getDatabase(intent.context)
            }
        }
    }

    private fun sendEvent(event: SignUpEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun submitSignUp() {
        val current = _state.value
        var usernameError = ""
        var passwordError = ""
        var confirmPasswordError = ""
        var emailError = ""

        if (!Regex("^[a-z0-9]+$").matches(current.username) || current.username.isEmpty()) {
            _state.update {
                it.copy(username = "")
            }
            usernameError = "Invalid format"
        } else {
            usernameError = ""
        }
        if (!Regex("^[a-zA-Z0-9]+$").matches(current.password) || current.password.isEmpty()) {
            _state.update {
                it.copy(password = "")
            }
            passwordError = "Invalid format"
        } else {
            passwordError = ""
        }

        if (!Regex("^[a-z0-9]+$").matches(current.confirmPassword) || current.confirmPassword.isEmpty()
            || current.confirmPassword != current.password
        ) {
            _state.update {
                it.copy(confirmPassword = "")
            }
            confirmPasswordError = "Invalid format"
        } else {
            confirmPasswordError = ""
        }

        if (!Regex("^[a-zA-Z0-9._@-]+$").matches(current.email) || current.email.isEmpty() || !current.email.endsWith(
                "@apero.vn"
            )
        ) {
            _state.update {
                it.copy(email = "")
            }
            emailError = "Invalid format"
        } else {
            emailError = ""
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    usernameError = usernameError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                    emailError = emailError
                )
            }
        }

        var hasError = confirmPasswordError.isEmpty() && passwordError.isEmpty() &&
                usernameError.isEmpty() && emailError.isEmpty()

        viewModelScope.launch(Dispatchers.IO) {
            val userDao = db?.userDao()
            if (userDao?.checkExistAccount(_state.value.username) != null) {
                sendEvent(SignUpEvent.showSignUpMessage("Account already exists"))
                hasError = false
            } else {
                if (hasError) {
                    userDao?.insertAll(
                        User(
                            id = 0,
                            name = null,
                            describe = null,
                            university = null,
                            phone = null,
                            avatar = null,
                            password = _state.value.password,
                            email = _state.value.email,
                            username = _state.value.username
                        )
                    )
                    sendEvent(SignUpEvent.showSignUpMessage("Sign up success"))
                }
            }
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSubmitSignUp = hasError
                )
            }
        }
    }
}