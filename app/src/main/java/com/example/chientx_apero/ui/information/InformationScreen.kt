package com.example.chientx_apero.ui.information

import android.content.Intent
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.components.Input
import com.example.chientx_apero.ui.information.components.Avatar
import com.example.chientx_apero.ui.information.components.ButtonInformation
import com.example.chientx_apero.ui.information.components.HeaderInformation
import com.example.chientx_apero.ui.information.components.PopupLayout
import com.example.chientx_apero.ui.theme.AppTheme

@Composable
fun InformationScreen(
    onClickBack: () -> Unit,
    viewModel: InformationViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var name by remember { mutableStateOf(AppCache.currentUser?.name ?: "") }
    var phone by remember { mutableStateOf(AppCache.currentUser?.phone ?: "") }
    var university by remember { mutableStateOf(AppCache.currentUser?.university ?: "") }
    var describe by remember { mutableStateOf(AppCache.currentUser?.describe ?: "") }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = PickVisualMedia()
    ) { uri ->
        uri?.let {
            try {
//                Recapture the content:// (it remove permission if you out session)
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                Log.e("Avatar", "No have permission")
            }

            viewModel.processIntent(InformationIntent.UpdateImageUri(it, context))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when(event){
                is InformationEvent.ShowMessageInformation -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
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
                    editStatus = state.canEditStatus,
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
                    editStatus = state.canEditStatus,
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
                        value = name,
                        onValueChange = {
                            name = it
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
                        value = phone,
                        onValueChange = {
                            phone = it
                        },
                        textError = state.phoneError,
                        enabledStatus = state.enabledStatus,
                        keyboardType = KeyboardType.Number
                    )
                }
                Input(
                    text = "University Name",
                    placeholder = "Your university number...",
                    value = university,
                    onValueChange = {
                        university = it
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
                    value = describe,
                    onValueChange = {
                        describe = it
                    },
                    textError = state.describeError,
                    enabledStatus = state.enabledStatus,
                    keyboardType = KeyboardType.Text
                )
                ButtonInformation(
                    onClickButtonInformation = {
                        viewModel.processIntent(
                            InformationIntent.SubmitInformation(
                                context = context,
                                name = name,
                                phone = phone,
                                university = university,
                                describe = describe
                            )
                        )
                    },
                    editStatus = state.canEditStatus
                )
            }
            if (state.isShowPopup) {
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


