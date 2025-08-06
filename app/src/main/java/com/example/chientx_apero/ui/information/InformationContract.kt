package com.example.chientx_apero.ui.information

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class InformationState(
    val nameError: String = "",
    val phoneError: String = "",
    val universityError: String = "",
    val describeError: String = "",
    val isShowPopup: Boolean = false,
    val canEditStatus: Boolean = false,
    val currentTheme: ThemeData = darkTheme,
    val imageUri: Uri? = AppCache.currentUser?.avatar
    ){
    val enabledStatus: Boolean get() = canEditStatus
}


sealed interface InformationIntent {
    data class UpdateImageUri(val imageUri: Uri, val context: Context) : InformationIntent
    data object ToggleCurrentTheme : InformationIntent
    data object ToggleEditStatus : InformationIntent
    data object HidePopUp : InformationIntent
    data class SubmitInformation(val context: Context, val phone: String, val name: String, val university: String, val describe: String) : InformationIntent
    data class SelectImage(val imagePickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>): InformationIntent
}

sealed interface InformationEvent {
    data class ShowMessageInformation(val message: String): InformationEvent
}