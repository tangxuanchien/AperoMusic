package com.example.chientx_apero.ui.player


import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.repository.SongRepository
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
    val current = state.value

    fun processIntent(intent: PlayerIntent, context: Context) {
        viewModelScope.launch {
            val repository = SongRepository(context)
            val song = repository.getSongById(AppCache.playingSong!!.id)

            when (intent) {
                is PlayerIntent.TogglePlayback -> {
                    var isPlaySong = state.value.isPlaySong
                    val intent = Intent(context, MusicService::class.java).apply {
                        when {
                            isPlaySong -> {
                                action = MusicService.ACTION_PAUSE
                                isPlaySong = false
                            }

                            !isPlaySong -> {
                                action = MusicService.ACTION_RESUME
                                isPlaySong = true
                            }
                        }
                    }
                    context.startService(intent)

                    _state.update {
                        it.copy(
                            isPlaySong = isPlaySong
                        )
                    }
                }

                is PlayerIntent.NextSong -> {
                    AppCache.playingSong = repository.getSongById(song.id + 1L)
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                }

                is PlayerIntent.PreviousSong -> {
                }

                is PlayerIntent.RandomSong -> {

                }

                is PlayerIntent.ReplaySong -> {

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