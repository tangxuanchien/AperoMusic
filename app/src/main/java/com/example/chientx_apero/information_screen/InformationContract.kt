package com.example.chientx_apero.information_screen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.example.chientx_apero.model.UserInformation
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class InformationState(
    val name: String = UserInformation.name,
    val phone: String = UserInformation.phone,
    val university: String = UserInformation.university,
    val describe: String = UserInformation.describe,
    val nameError: String = "",
    val phoneError: String = "",
    val universityError: String = "",
    val describeError: String = "",
    val showPopup: Boolean = false,
    val editStatus: Boolean = false,
    val currentTheme: ThemeData = darkTheme,
    val imageUri: Any = UserInformation.imageUri
){
    val enabledStatus: Boolean get() = editStatus
}


sealed interface InformationIntent {
    data class NameChanged(val name: String) : InformationIntent
    data class PhoneChanged(val phone: String) : InformationIntent
    data class UniversityChanged(val university: String) : InformationIntent
    data class DescribeChanged(val describe: String) : InformationIntent
    data class UpdateImageUri(val imageUri: String) : InformationIntent
    data object ToggleCurrentTheme : InformationIntent
    data object ToggleEditStatus : InformationIntent
    data object HidePopUp : InformationIntent
    data object SubmitInformation : InformationIntent
    data class SelectImage(val imagePickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>): InformationIntent
}