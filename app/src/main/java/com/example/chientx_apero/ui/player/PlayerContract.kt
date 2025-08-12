package com.example.chientx_apero.ui.player

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.Song

data class PlayerState(
    val isPlaySong: Boolean = true,
    val song: Song? = AppCache.playingSong,
    val currentTime: Int = 0,
    val duration: Long = 0
)

sealed interface PlayerIntent {
    data object TogglePlayback : PlayerIntent
    data object RandomSong : PlayerIntent
    data object PreviousSong : PlayerIntent
    data object NextSong : PlayerIntent
    data object ReplaySong : PlayerIntent
    data object GetCurrentTimeSong : PlayerIntent
    data class SeekToProgress(val progress: Float): PlayerIntent
}

sealed interface PlayerEvent {
    data class showPlayerEvent(val message: String): PlayerEvent
}