package com.example.chientx_apero.ui.player

import android.content.Context
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.Song

data class PlayerState(
    val isPlaySong: Boolean = true,
    val song: Song? = AppCache.playingSong,
    val currentTime: Int = 0
)

sealed interface PlayerIntent {
    data object TogglePlayback : PlayerIntent
    data object RandomSong : PlayerIntent
    data object PreviousSong : PlayerIntent
    data object NextSong : PlayerIntent
    data object ReplaySong : PlayerIntent
    data object GetCurrentTimeSong : PlayerIntent
}

sealed interface PlayerEvent {
    data class showPlayerEvent(val message: String): PlayerEvent
}