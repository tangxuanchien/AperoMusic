package com.example.chientx_apero.service

import android.content.Context
import android.content.Intent
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.ui.player.PlayerEvent
import com.example.chientx_apero.ui.player.PlayerIntent
import com.example.chientx_apero.ui.player.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object MusicManager {
    private val _state = MutableStateFlow<PlayerState>(PlayerState())
    val state: StateFlow<PlayerState> = _state
    private val _event = MutableSharedFlow<PlayerEvent>()
    val event: SharedFlow<PlayerEvent> = _event.asSharedFlow()
    val current = state.value

    private var scope: CoroutineScope? = null
    fun init(appScope: CoroutineScope) {
        scope = appScope
    }

    fun processIntent(intent: PlayerIntent, context: Context) {
        scope?.launch {
            val repository = SongRepository(context)

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
                            isPlaySong = isPlaySong,
                            duration = parseDurationToMilliseconds(AppCache.playingSong!!.duration)
                        )
                    }
                }

                is PlayerIntent.NextSong -> {
                    val song = repository.getNextSong(
                        AppCache.playingSong!!.id,
                        AppCache.playlist!!.id.toLong()
                    )
                    AppCache.playingSong =
                        song ?: repository.getFirstSongInPlaylist(AppCache.playlist!!.id.toLong())
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                    _state.update {
                        it.copy(
                            isPlaySong = true,
                            song = AppCache.playingSong,
                            duration = parseDurationToMilliseconds(AppCache.playingSong!!.duration)
                        )
                    }
                }

                is PlayerIntent.PreviousSong -> {
                    val song = repository.getPreviousSong(
                        AppCache.playingSong!!.id,
                        AppCache.playlist!!.id.toLong()
                    )
                    AppCache.playingSong =
                        song ?: repository.getLastSongInPlaylist(AppCache.playlist!!.id.toLong())
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                    _state.update {
                        it.copy(
                            isPlaySong = true,
                            song = AppCache.playingSong,
                            duration = parseDurationToMilliseconds(AppCache.playingSong!!.duration)
                        )
                    }
                }

                is PlayerIntent.RandomSong -> {
                    AppCache.playingSong = repository.getSongByIdRandom(
                        AppCache.playlist!!.id.toLong(),
                        AppCache.playingSong!!.id
                    )
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_PLAY
                        putExtra("uri", AppCache.playingSong!!.data.toString())
                    }
                    context.startService(intent)
                    _state.update {
                        it.copy(
                            isPlaySong = true,
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

                is PlayerIntent.SeekToProgress -> {
                    val intent = Intent(context, MusicService::class.java).apply {
                        action = MusicService.ACTION_SEEK_TO
                        putExtra("progress", intent.progress)
                    }
                    context.startService(intent)
                }
            }
        }
    }

    fun parseDurationToMilliseconds(durationStr: String): Long {
        val parts = durationStr.split(":")
        val minutes = parts[0].toLongOrNull() ?: 0
        val seconds = parts[1].toLongOrNull() ?: 0
        return (minutes * 60 + seconds) * 1000
    }
}