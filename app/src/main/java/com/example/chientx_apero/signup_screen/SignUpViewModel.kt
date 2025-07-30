package com.example.chientx_apero.signup_screen


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _state = MutableStateFlow<SignUpState>(SignUpState())
    val state: StateFlow<SignUpState> = _state

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

        val hasError = confirmPasswordError.isEmpty() && passwordError.isEmpty() &&
                usernameError.isEmpty() && emailError.isEmpty()

        _state.update {
            it.copy(
                usernameError = usernameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                emailError = emailError,
                isSubmitSignUp = hasError
            )
        }

    }
}