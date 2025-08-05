package com.example.chientx_apero.ui.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.components.Input

data class User(
    var name: String,
    var password: String
)

@Composable
fun SignUpScreen(
    onClickLogin: (username: String, password: String) -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.processIntent(SignUpIntent.ProvideContext(context))
        viewModel.event.collect { event ->
            when(event){
                is SignUpEvent.showSignUpMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(state.isSubmitSignUp) {
        if (state.isSubmitSignUp) {
            onClickLogin(state.username, state.password)
            viewModel.processIntent(SignUpIntent.ResetIsSubmitSignUp)
        }
    }

    MaterialTheme(
        colorScheme = state.currentTheme.color,
        typography = state.currentTheme.typography
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.back),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 30.dp, start = 15.dp)
                        .size(30.dp)
                        .clickable(
                            onClick = { onClickLogin("", "") }
                        )
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_app),
                            contentDescription = "Logo App",
                            modifier = Modifier
                                .size(250.dp)
                                .align(Alignment.Center)
                        )
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                    Input(
                        placeholder = "Username",
                        value = state.username,
                        onValueChange = {
                            viewModel.processIntent(SignUpIntent.UsernameChanged(it))
                        },
                        textError = state.usernameError,
                        leadingIcon = R.drawable.user
                    )
                    Input(
                        placeholder = "Password",
                        value = state.password,
                        onValueChange = {
                            viewModel.processIntent(SignUpIntent.PasswordChanged(it))
                        },
                        textError = state.passwordError,
                        leadingIcon = R.drawable.lock,
                        trailingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (state.passwordVisible) R.drawable.eye
                                    else R.drawable.no_eye
                                ),
                                contentDescription = "Toggle Password Visibility",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        viewModel.processIntent(SignUpIntent.TogglePasswordVisibility)
                                    },
                                tint = Color(0xFF808080)
                            )
                        },
                        visualTransformation = state.visualTransformationPassword
                    )
                    Input(
                        placeholder = "Confirm Password",
                        value = state.confirmPassword,
                        onValueChange = {
                            viewModel.processIntent(SignUpIntent.ConfirmPasswordChanged(it))
                        },
                        textError = state.confirmPasswordError,
                        leadingIcon = R.drawable.lock,
                        trailingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (state.confirmPasswordVisible) R.drawable.eye
                                    else R.drawable.no_eye
                                ),
                                contentDescription = "Toggle Password Visibility",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        viewModel.processIntent(SignUpIntent.ToggleConfirmPasswordVisibility)
                                    },
                                tint = Color(0xFF808080)
                            )
                        },
                        visualTransformation = state.visualTransformationConfirmPassword
                    )
                    Input(
                        placeholder = "Email",
                        value = state.email,
                        onValueChange = {
                            viewModel.processIntent(SignUpIntent.EmailChanged(it))
                        },
                        textError = state.emailError,
                        leadingIcon = R.drawable.email,
                    )
                }
                Button(
                    onClick = {
                        viewModel.processIntent(SignUpIntent.SubmitSignUp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 35.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}