package com.example.chientx_apero.information_screen

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.UserInformation
import com.example.chientx_apero.ui.theme.darkTheme
import com.example.chientx_apero.ui.theme.lightTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class InformationViewModel : ViewModel() {
    private val _state = MutableStateFlow<InformationState>(InformationState())
    val state: StateFlow<InformationState> = _state

    fun processIntent(intent: InformationIntent) {
        when (intent) {
            is InformationIntent.DescribeChanged -> {
                _state.update {
                    it.copy(
                        describe = intent.describe,
                    )
                }
            }

            is InformationIntent.NameChanged -> {
                var nameError = ""
                if (!Regex("^[\\p{L}\\s]+\$").matches(intent.name)) {
                    nameError = "Invalid error"
                } else if (Regex("^[\\p{L}\\s]+\$").matches(intent.name) and !nameError.isEmpty()) {
                    nameError = ""
                }
                _state.update {
                    it.copy(
                        name = intent.name,
                        nameError = nameError
                    )
                }
            }

            is InformationIntent.PhoneChanged -> {
                var phoneError = ""
                if (!Regex("^\\d+\$").matches(intent.phone)) {
                    phoneError = "Invalid error"
                } else if (Regex("^\\d+\$").matches(intent.phone) and !phoneError.isEmpty()) {
                    phoneError = ""
                }
                _state.update {
                    it.copy(
                        phone = intent.phone,
                        phoneError = phoneError
                    )
                }
            }

            is InformationIntent.ToggleCurrentTheme -> {
                var currentTheme = _state.value.currentTheme
                if (_state.value.currentTheme == lightTheme) {
                    currentTheme = darkTheme
                } else {
                    currentTheme = lightTheme
                }
                _state.update {
                    it.copy(
                        currentTheme = currentTheme
                    )
                }
            }

            is InformationIntent.UniversityChanged -> {
                var universityError = ""
                if (!Regex("^[\\p{L}\\s]+\$").matches(intent.university)) {
                    universityError = "Invalid error"
                } else if (Regex("^[\\p{L}\\s]+\$").matches(intent.university) and !universityError.isEmpty()) {
                    universityError = ""
                }
                _state.update {
                    it.copy(
                        university = intent.university,
                        universityError = universityError
                    )
                }
            }

            is InformationIntent.UpdateImageUri -> {
                _state.update {
                    it.copy(
                        imageUri = intent.imageUri
                    )
                }
                UserInformation.imageUri = intent.imageUri
            }

            InformationIntent.ToggleEditStatus -> {
                _state.update {
                    it.copy(
                        editStatus = !_state.value.editStatus
                    )
                }
            }

            InformationIntent.SubmitInformation -> {
                val current = _state.value
                var nameError = ""
                var phoneError = ""
                var universityError = ""
                var describeError = ""

                if (!Regex("^[A-Za-z0-9\\s]+\$").matches(current.describe)) {
                    describeError = "Invalid error"
                } else if (Regex("^[A-Za-z0-9\\s]+\$").matches(current.describe) and !describeError.isEmpty()) {
                    describeError = ""
                }
                if (current.name.isEmpty()) {
                    nameError = "Empty name"
                }
                if (current.phone.isEmpty()) {
                    phoneError = "Empty phone"
                }
                if (current.university.isEmpty()) {
                    universityError = "Empty university"
                }
                if (current.describe.isEmpty()) {
                    describeError = "Empty describe"
                }

                _state.update {
                    it.copy(
                        nameError = nameError,
                        phoneError = phoneError,
                        universityError = universityError,
                        describeError = describeError
                    )
                }

                if (nameError.isEmpty() and phoneError.isEmpty() and universityError.isEmpty() and describeError.isEmpty()) {
                    _state.update {
                        it.copy(
                            showPopup = true
                        )
                    }
                    UserInformation.name = current.name
                    UserInformation.phone = current.phone
                    UserInformation.university = current.university
                    UserInformation.describe = current.describe
                }
            }

            is InformationIntent.SelectImage -> {
                intent.imagePickerLauncher.launch(
                    PickVisualMediaRequest(PickVisualMedia.ImageOnly)
                )
            }

            is InformationIntent.HidePopUp -> {
                _state.update {
                    it.copy(showPopup = false, editStatus = false)
                }
            }
        }
    }
}