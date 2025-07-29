package com.example.chientx_apero.information_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chientx_apero.UserInformation
import com.example.chientx_apero.ui.theme.AppTheme
import com.example.chientx_apero.ui.theme.darkTheme
import com.example.chientx_apero.ui.theme.lightTheme

@Composable
fun InformationScreen(
    onClickBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf(UserInformation.name) }
    var phone by remember { mutableStateOf(UserInformation.phone) }
    var university by remember { mutableStateOf(UserInformation.university) }
    var describe by remember { mutableStateOf(UserInformation.describe) }

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

    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Any?>(UserInformation.imageUri) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            imageUri.value = uri
            UserInformation.imageUri = uri
        }
    }

    var currentTheme by remember { mutableStateOf(darkTheme) }

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
                    .padding(15.dp)
                    .clickable() { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderInformation(
                    currentTheme = currentTheme,
                    editStatus = editStatus,
                    onToggleEditStatus = { editStatus = !editStatus },
                    onToggleCurrentTheme = {
                        if (currentTheme == lightTheme) {
                            currentTheme = darkTheme
                        } else currentTheme = lightTheme
                    }
                )
                Avatar(
                    context = context,
                    imageUri = imageUri,
                    editStatus = editStatus,
                    imagePickerLauncher = imagePickerLauncher
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
                    Spacer(modifier = Modifier.padding(10.dp))
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
                ButtonInformation(
                    onClickButtonInformation = {
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
                                UserInformation.name = name
                                UserInformation.phone = phone
                                UserInformation.university = university
                                UserInformation.describe = describe
                            }
                        }
                    },
                    editStatus = editStatus
                )
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
        InformationScreen(
            onClickBack = {}
        )
    }
}


