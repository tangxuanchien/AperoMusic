package com.example.chientx_apero.ui.player

import android.content.Context
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

data class PlayerState(
    val isPlaySong: Boolean = false,
    val isStartPlaySong: Boolean = false
)

sealed interface PlayerIntent {
    data class PlaySong(val context: Context) : PlayerIntent
    data class PauseSong(val context: Context) : PlayerIntent
}

sealed interface PlayerEvent {
    data class showPlayerEvent(val message: String): PlayerEvent
}