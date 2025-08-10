package com.example.chientx_apero.ui.player


import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.service.MusicService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _state = MutableStateFlow<PlayerState>(PlayerState())
    val state: StateFlow<PlayerState> = _state
    private val _event = MutableSharedFlow<PlayerEvent>()
    val event: SharedFlow<PlayerEvent> = _event.asSharedFlow()

    fun processIntent(intent: PlayerIntent, context: Context) {
        when (intent) {
            is PlayerIntent.PlaySong -> {
                val intent = Intent(context, MusicService::class.java).apply {
                    if (state.value.isStartPlaySong) {
                        action = MusicService.ACTION_RESUME
                    } else {
                        action = MusicService.ACTION_PLAY
                        putExtra(
                            "uri",
                            "/storage/emulated/0/Download/Chăm Hoa - MONO - SoundLoadMate.com.mp3"
                        )
                        _state.update {
                            it.copy(
                                isStartPlaySong = true
                            )
                        }
                    }
                }
                context.startService(intent)

                _state.update {
                    it.copy(
                        isPlaySong = true
                    )
                }
            }

            is PlayerIntent.PauseSong -> {
                val intent = Intent(context, MusicService::class.java).apply {
                    action = MusicService.ACTION_PAUSE
                }
                context.startService(intent)
                _state.update {
                    it.copy(
                        isPlaySong = false
                    )
                }
            }
        }
    }

    private fun sendEvent(event: PlayerEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}