package com.example.chientx_apero.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.information_screen.Input


@Composable
fun LoginScreen(
    onClickSignUp: () -> Unit,
    onClickHome: () -> Unit,
    username: String,
    password: String,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.processIntent(LoginIntent.SetInitialData(username, password))
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
            Box {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
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
                            text = "Login to your account",
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
                            viewModel.processIntent(LoginIntent.UsernameChanged(it))
                        },
                        textError = state.usernameError,
                        leadingIcon = R.drawable.user
                    )
                    Input(
                        placeholder = "Password",
                        value = state.password,
                        onValueChange = {
                            viewModel.processIntent(LoginIntent.PasswordChanged(it))
                        },
                        textError = state.passwordError,
                        leadingIcon = R.drawable.lock,
                        trailingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (state.passwordVisible) {
                                        R.drawable.eye
                                    } else {
                                        R.drawable.no_eye
                                    }
                                ),
                                contentDescription = "Toggle Password Visibility",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        viewModel.processIntent(LoginIntent.TogglePasswordVisibility)
                                    },
                                tint = Color(0xFF808080)
                            )
                        },
                        visualTransformation = if (!state.passwordVisible) {
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Checkbox(
                            checked = state.checked,
                            onCheckedChange = {},
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.surfaceTint
                            )
                        )

                        Text(
                            text = "Remember me",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Button(
                        onClick = onClickHome,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Log in",
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.CenterVertically),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                ) {
                    Text(
                        text = "Don't have an account?",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.surfaceTint,
                        modifier = Modifier.clickable(
                            onClick = onClickSignUp
                        )
                    )
                }
            }
        }
    }
}