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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.ui.theme.AppTheme

@Composable
fun InformationScreen(
    onClickBack: () -> Unit,
    viewModel: InformationViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.processIntent(InformationIntent.UpdateImageUri(uri.toString()))
        }
    }

    MaterialTheme(
        colorScheme = state.currentTheme.color,
        typography = state.currentTheme.typography,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .clickable { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderInformation(
                    currentTheme = state.currentTheme,
                    editStatus = state.editStatus,
                    onToggleEditStatus = {
                        viewModel.processIntent(InformationIntent.ToggleEditStatus)
                    },
                    onToggleCurrentTheme = {
                        viewModel.processIntent(InformationIntent.ToggleCurrentTheme)
                    }
                )
                Avatar(
                    context = context,
                    imageUri = state.imageUri,
                    editStatus = state.editStatus,
                    onClickSelectImage = {
                        viewModel.processIntent(InformationIntent.SelectImage(imagePickerLauncher))
                    }
                )
                Row(
                ) {
                    Input(
                        text = "Name",
                        placeholder = "Enter your name...",
                        modifier = Modifier.weight(1f),
                        value = state.name,
                        onValueChange = {
                            viewModel.processIntent(InformationIntent.NameChanged(it))
                        },
                        textError = state.nameError,
                        enabledStatus = state.enabledStatus,
                        keyboardType = KeyboardType.Text
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Input(
                        text = "Phone Number",
                        placeholder = "Your phone number...",
                        modifier = Modifier.weight(1f),
                        value = state.phone,
                        onValueChange = {
                            viewModel.processIntent(InformationIntent.PhoneChanged(it))
                        },
                        textError = state.phoneError,
                        enabledStatus = state.enabledStatus,
                        keyboardType = KeyboardType.Number
                    )
                }
                Input(
                    text = "University Name",
                    placeholder = "Your university number...",
                    value = state.university,
                    onValueChange = {
                        viewModel.processIntent(InformationIntent.UniversityChanged(it))
                    },
                    textError = state.universityError,
                    enabledStatus = state.enabledStatus,
                    keyboardType = KeyboardType.Text
                )
                Input(
                    text = "Describe Yourself",
                    placeholder = "Enter a description about yourself...",
                    modifier = Modifier
                        .fillMaxWidth(),
                    size = 200.dp,
                    value = state.describe,
                    onValueChange = {
                        viewModel.processIntent(InformationIntent.DescribeChanged(it))
                    },
                    textError = state.describeError,
                    enabledStatus = state.enabledStatus,
                    keyboardType = KeyboardType.Text
                )
                ButtonInformation(
                    onClickButtonInformation = {
                        viewModel.processIntent(InformationIntent.SubmitInformation)
                    },
                    editStatus = state.editStatus
                )
            }
            if (state.showPopup) {
                PopupLayout(
                    onDismiss = { viewModel.processIntent(InformationIntent.HidePopUp) }
                )
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


