package com.example.chientx_apero.ui.player_bar

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.service.MusicService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log

class PlayerBarViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow<PlayerBarState>(PlayerBarState())
    val state: StateFlow<PlayerBarState> = _state

    fun processIntent(intent: PlayerBarIntent, context: Context) {
        val current = _state.value
        when (intent) {
            is PlayerBarIntent.HandleSongAction -> {
                var isPlaySong = AppCache.isPlayingSong.value
                val isSameSong = intent.song == AppCache.playingSong
                val intentService = Intent(context, MusicService::class.java).apply {
                    when {
//                        Case 1: Play other song (not playing)
                        !isPlaySong && !isSameSong -> {
                            action = MusicService.ACTION_PLAY
                            putExtra("uri", intent.song.data.toString())
                            isPlaySong = true
                        }
//                       Case 2: Pause song
                        isPlaySong && isSameSong -> {
                            action = MusicService.ACTION_PAUSE
                            isPlaySong = false
                        }
//                        Case 3: Resume song
                        !isPlaySong && isSameSong -> {
                            action = MusicService.ACTION_RESUME
                            isPlaySong = true
                        }
//                        Case 4: Play other song (playing)
                        isPlaySong && !isSameSong -> {
                            action = MusicService.ACTION_PLAY
                            putExtra("uri", intent.song.data.toString())
                            isPlaySong = true
                        }
                    }
                }
                context.startService(intentService)
                AppCache.isPlayingSong.value = isPlaySong
                AppCache.playingSong = intent.song
            }

            PlayerBarIntent.StopSong -> {
                val intent = Intent(context, MusicService::class.java).apply {
                    action = MusicService.ACTION_STOP
                }
                context.startService(intent)
                AppCache.playingSong = null
                AppCache.isPlayingSong.value = false
            }
        }
    }
}
