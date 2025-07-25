package com.example.chientx_apero.information_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.theme.AppTheme
import com.example.chientx_apero.ui.theme.darkTheme
import com.example.chientx_apero.ui.theme.lightTheme

@Composable
fun ScreenDefault() {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var describe by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var universityError by remember { mutableStateOf("") }
    var describeError by remember { mutableStateOf("") }

    var showPopup by remember { mutableStateOf(false) }
    var editStatus by remember { mutableStateOf(false) }
    var enabledStatus by remember { mutableStateOf(false) }
    if (editStatus) {
        enabledStatus = true
    }

    var currentTheme by remember { mutableStateOf(lightTheme) }

    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable() { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (currentTheme == lightTheme) {
                                R.drawable.dark_theme
                            } else R.drawable.light_theme
                        ),
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterStart)
                            .clickable {
                                if (currentTheme == lightTheme) {
                                    currentTheme = darkTheme
                                } else currentTheme = lightTheme
                            },
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "My Information".uppercase(),
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (!editStatus) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.edit_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    editStatus = true
                                },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
                Row(
                ) {
                    Input(
                        text = "Name",
                        placeholder = "Enter your name...",
                        modifier = Modifier.weight(1f),
                        value = name,
                        onValueChange = {
                            name = it
                            if (!Regex("^[\\p{L}\\s]+\$").matches(it)) {
                                nameError = "Invalid error"
                            } else if (Regex("^[\\p{L}\\s]+\$").matches(it) and !nameError.isEmpty()) {
                                nameError = ""
                            }
                        },
                        textError = nameError,
                        enabledStatus = enabledStatus,
                        keyboardType = KeyboardType.Text
                    )
                    Input(
                        text = "Phone Number",
                        placeholder = "Your phone number...",
                        modifier = Modifier.weight(1f),
                        value = phone,
                        onValueChange = {
                            phone = it
                            if (!Regex("^\\d+\$").matches(it)) {
                                phoneError = "Invalid error"
                            } else if (Regex("^\\d+\$").matches(it) and !phoneError.isEmpty()) {
                                phoneError = ""
                            }
                        },
                        textError = phoneError,
                        enabledStatus = enabledStatus,
                        keyboardType = KeyboardType.Number
                    )
                }
                Input(
                    text = "University Name",
                    placeholder = "Your university number...",
                    value = university,
                    onValueChange = {
                        university = it
                        if (!Regex("^[\\p{L}\\s]+\$").matches(it)) {
                            universityError = "Invalid error"
                        } else if (Regex("^[\\p{L}\\s]+\$").matches(it) and !universityError.isEmpty()) {
                            universityError = ""
                        }
                    },
                    textError = universityError,
                    enabledStatus = enabledStatus,
                    keyboardType = KeyboardType.Text
                )
                Input(
                    text = "Describe Yourself",
                    placeholder = "Enter a description about yourself...",
                    modifier = Modifier
                        .fillMaxWidth(),
                    size = 200.dp,
                    value = describe,
                    onValueChange = {
                        describe = it
                        if (!Regex("^[A-Za-z0-9\\s]+\$").matches(it)) {
                            universityError = "Invalid error"
                        } else if (Regex("^[A-Za-z0-9\\s]+\$").matches(it) and !universityError.isEmpty()) {
                            universityError = ""
                        }
                    },
                    textError = describeError,
                    enabledStatus = enabledStatus,
                    keyboardType = KeyboardType.Text
                )
                if (editStatus) {
                    Button(
                        onClick = {
                            if (name.isEmpty()) {
                                nameError = "Empty name"
                            } else if (phone.isEmpty()) {
                                phoneError = "Empty phone"
                            } else if (university.isEmpty()) {
                                universityError = "Empty university"
                            } else if (describe.isEmpty()) {
                                describeError = "Empty describe"
                            } else {
                                if (nameError.isEmpty() and phoneError.isEmpty() and universityError.isEmpty() and describeError.isEmpty()) {
                                    showPopup = true
                                }
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .padding(bottom = 40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceTint
                        )
                    ) {
                        Text(
                            text = "Submit",
                            modifier = Modifier
                                .padding(vertical = 15.dp, horizontal = 35.dp)
                        )
                    }
                }
            }
            if (showPopup) {
                PopupLayout(
                    onDismiss = { showPopup = false }
                )
                editStatus = false
                enabledStatus = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    AppTheme {
        ScreenDefault()
    }
}