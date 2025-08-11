package com.example.chientx_apero.ui.player


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.service.MusicService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class PlayerViewModel : ViewModel() {
    private val _state = MutableStateFlow<PlayerState>(PlayerState())
    val state: StateFlow<PlayerState> = _state
    private val _event = MutableSharedFlow<PlayerEvent>()
    val event: SharedFlow<PlayerEvent> = _event.asSharedFlow()
    val current = state.value

    private var mediaPlayerService: WeakReference<MusicService>? = null
    private var isServiceBound = false
    private var serviceConnection: ServiceConnection? = null

    fun bindService(context: Context) {
        if (serviceConnection != null) return

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as MusicService.MusicBinder
                mediaPlayerService = WeakReference(binder.getService())
                isServiceBound = true

                viewModelScope.launch {
                    mediaPlayerService?.get()?.currentPosition?.collect { pos ->
                        _state.update { it.copy(currentTime = pos) }
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isServiceBound = false
                mediaPlayerService = null
            }
        }

        val intent = Intent(context, MusicService::class.java)
        context.bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context) {
        serviceConnection?.let {
            context.unbindService(it)
            serviceConnection = null
        }
        isServiceBound = false
        mediaPlayerService = null
    }

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
                    Log.d("Play", "true")
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
                    _state.update {
                        it.copy(
                            song = AppCache.playingSong
                        )
                    }
                }

                is PlayerIntent.PreviousSong -> {
                    AppCache.playingSong = repository.getSongById(song.id - 1L)
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                    _state.update {
                        it.copy(
                            song = AppCache.playingSong
                        )
                    }
                }

                is PlayerIntent.RandomSong -> {
                    AppCache.playingSong = repository.getSongByIdRandom()
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                    _state.update {
                        it.copy(
                            song = AppCache.playingSong
                        )
                    }
                }

                is PlayerIntent.ReplaySong -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_REPLAY
                    }
                    context.startService(intent)
                }

                is PlayerIntent.GetCurrentTimeSong -> {
                    if (!isServiceBound) {
                        bindService(context)
                    }
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