package com.example.chientx_apero.ui.information

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.SessionUser
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.ui.theme.darkTheme
import com.example.chientx_apero.ui.theme.lightTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InformationViewModel() : ViewModel() {
    private val _state = MutableStateFlow<InformationState>(InformationState())
    val state: StateFlow<InformationState> = _state
    private var db: AppDatabase? = null

    fun processIntent(intent: InformationIntent) {
        when (intent) {
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

            is InformationIntent.UpdateImageUri -> {
                _state.update {
                    it.copy(
                        imageUri = intent.imageUri
                    )
                }
                viewModelScope.launch {
                    val db = AppDatabase.getDatabase(intent.context)
                    db.userDao().updateAvatar(
                        avatar = intent.imageUri,
                        username = SessionUser.currentUser?.username!!
                    )
                }
            }

            InformationIntent.ToggleEditStatus -> {
                _state.update {
                    it.copy(
                        editStatus = !_state.value.editStatus
                    )
                }
            }

            is InformationIntent.SubmitInformation -> {
                var nameError = ""
                var phoneError = ""
                var universityError = ""
                var describeError = ""

                if (!Regex("^[A-Za-z0-9\\s]+\$").matches(intent.describe)) {
                    describeError = "Invalid error"
                } else if (Regex("^[A-Za-z0-9\\s]+\$").matches(intent.describe) and !describeError.isEmpty()) {
                    describeError = ""
                }

                if (!Regex("^[\\p{L}\\s]+\$").matches(intent.name)) {
                    nameError = "Invalid error"
                } else if (Regex("^[\\p{L}\\s]+\$").matches(intent.name) and !nameError.isEmpty()) {
                    nameError = ""
                }

                if (!Regex("^\\d+\$").matches(intent.phone)) {
                    phoneError = "Invalid error"
                } else if (Regex("^\\d+\$").matches(intent.phone) and !phoneError.isEmpty()) {
                    phoneError = ""
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

                    viewModelScope.launch {
                        val db = AppDatabase.getDatabase(intent.context)
                        db.userDao().updateInformation(
                            name = intent.name,
                            phone = intent.phone,
                            university = intent.university,
                            describe = intent.describe,
                            username = SessionUser.currentUser?.username!!
                        )
                        SessionUser.currentUser = db.userDao().findByUsername(SessionUser.currentUser?.username!!)
                    }
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

            is InformationIntent.ProvideContext -> {
                db = AppDatabase.getDatabase(intent.context)
            }
        }
    }
}