package com.example.chientx_apero.login_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.information_screen.Input
import com.example.chientx_apero.ui.theme.darkTheme
import kotlin.jvm.java
import kotlin.text.Regex

data class User(
    var name: String,
    var password: String
)

@Composable
fun SignUpScreen(
    onClickLogin: (username: String, password: String) -> Unit
) {
    var userError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var currentTheme by remember { mutableStateOf(darkTheme) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography
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
                        value = username,
                        onValueChange = {
                            username = it
                            userError = ""
                        },
                        textError = userError,
                        leadingIcon = R.drawable.user
                    )
                    Input(
                        placeholder = "Password",
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = ""
                        },
                        textError = passwordError,
                        leadingIcon = R.drawable.lock,
                        trailingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (passwordVisible) R.drawable.outline_visibility_24
                                    else R.drawable.outline_visibility_off_24
                                ),
                                contentDescription = "Toggle Password Visibility",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { passwordVisible = !passwordVisible },
                                tint = Color(0xFF808080)
                            )
                        },
                        visualTransformation = if(!passwordVisible){
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        }
                    )
                    Input(
                        placeholder = "Confirm Password",
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = ""
                        },
                        textError = confirmPasswordError,
                        leadingIcon = R.drawable.lock,
                        trailingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    if (confirmPasswordVisible) R.drawable.outline_visibility_24
                                    else R.drawable.outline_visibility_off_24
                                ),
                                contentDescription = "Toggle Password Visibility",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { confirmPasswordVisible = !confirmPasswordVisible },
                                tint = Color(0xFF808080)
                            )
                        },
                        visualTransformation = if(!confirmPasswordVisible){
                            PasswordVisualTransformation()
                        } else {
                            VisualTransformation.None
                        }
                    )
                    Input(
                        placeholder = "Email",
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = ""
                        },
                        textError = emailError,
                        leadingIcon = R.drawable.email,
                    )
                }
                Button(
                    onClick = {
//                        Username
                        if (!Regex("^[a-z0-9]+$").matches(username) || username.isEmpty()) {
                            userError = "Invalid format"
                            username = ""
                        } else {
                            userError = ""
                        }
//                        Password
                        if (!Regex("^[a-zA-Z0-9]+$").matches(password) || password.isEmpty()) {
                            passwordError = "Invalid format"
                            password = ""
                        } else {
                            passwordError = ""
                        }

//                        Confirm Password
                        if (!Regex("^[a-z0-9]+$").matches(confirmPassword) || confirmPassword.isEmpty()
                            || confirmPassword != password
                        ) {
                            confirmPasswordError = "Invalid format"
                            confirmPassword = ""
                        } else {
                            confirmPasswordError = ""
                        }

//                        Email
                        if (!Regex("^[a-zA-Z0-9._@-]+$").matches(email) || email.isEmpty()
                            || !email.endsWith(
                                "@apero.vn"
                            )
                        ) {
                            emailError = "Invalid format"
                            email = ""
                        } else {
                            emailError = ""
                        }

                        if(confirmPasswordError.isEmpty() && passwordError.isEmpty() && userError.isEmpty() && emailError.isEmpty()){
                            onClickLogin(username, password)
                        }
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