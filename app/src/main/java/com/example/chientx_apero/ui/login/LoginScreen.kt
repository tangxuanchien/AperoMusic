package com.example.chientx_apero.ui.login

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.components.Input
import com.example.chientx_apero.ui.theme.darkTheme


@Composable
fun LoginScreen(
    onClickSignUp: () -> Unit,
    onClickHome: () -> Unit,
    username: String,
    password: String,
    viewModel: LoginViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var password by remember { mutableStateOf(password) }
    var username by remember { mutableStateOf(username) }
    var currentTheme by remember { mutableStateOf(darkTheme) }
    LaunchedEffect(state.isLoginSuccess) {
        if (state.isLoginSuccess) {
            onClickHome()
            viewModel.processIntent(LoginIntent.ResetLoginSuccess)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event) {
                is LoginEvent.ShowLoginMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography
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
                            text = stringResource(R.string.title_login),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                    Input(
                        placeholder = stringResource(R.string.username),
                        value = username,
                        onValueChange = { username = it },
                        textError = "",
                        leadingIcon = R.drawable.user
                    )
                    Input(
                        placeholder = stringResource(R.string.password),
                        value = password,
                        onValueChange = { password = it },
                        textError = "",
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
                        visualTransformation = state.visualTransformation
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
                            text = stringResource(R.string.remember_me),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.processIntent(
                                LoginIntent.CheckPermission(
                                    username,
                                    password,
                                    context
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.login),
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
                        text = stringResource(R.string.not_account),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = stringResource(R.string.sign_up),
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