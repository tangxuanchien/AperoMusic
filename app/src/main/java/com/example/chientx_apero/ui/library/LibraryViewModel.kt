package com.example.chientx_apero.ui.library

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.*
import com.example.chientx_apero.room_db.repository.PlaylistRepository
import com.example.chientx_apero.room_db.repository.PlaylistSongCrossRefRepository
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.ui.my_playlist.MyPlaylistEvent
import com.example.chientx_apero.ui.playlist.components.getAllMp3Files
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel() : ViewModel() {
    private val _state = MutableStateFlow<LibraryState>(LibraryState())
    val state: StateFlow<LibraryState> = _state
    private val _event = MutableSharedFlow<LibraryEvent>()
    val event: SharedFlow<LibraryEvent> = _event.asSharedFlow()

    fun processIntent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadSongs -> {
                CoroutineScope(Dispatchers.IO).launch {
                    _state.value.displayedSongs.clear()
                    _state.value.displayedSongs.addAll(AppCache.allSongs)
                }
            }

            is LibraryIntent.OpenMenu -> {
                var selectedSong = _state.value.selectedSong
                var expanded = _state.value.expanded

                if (selectedSong == intent.song && expanded) {
                    expanded = false
                    selectedSong = null
                } else {
                    expanded = true
                    selectedSong = intent.song
                }
                _state.update {
                    it.copy(expanded = expanded, selectedSong = selectedSong)
                }
            }

            is LibraryIntent.CloseMenu -> {
                _state.update {
                    it.copy(
                        expanded = false,
                        selectedSong = null
                    )
                }
            }

            LibraryIntent.HidePopUp -> {
                _state.update {
                    it.copy(
                        expanded = false
                    )
                }
            }

            is LibraryIntent.LoadPlaylists -> {
                viewModelScope.launch {
                    val repository = PlaylistRepository(intent.context)
                    _state.value.playlists.clear()
                    _state.value.playlists.addAll(repository.getAllPlaylists())
                }
            }

            is LibraryIntent.AddSongToPlaylist -> {
                viewModelScope.launch {
                    val playlistSongCrossRefRepository = PlaylistSongCrossRefRepository(intent.context)
                    val hasSongInPlaylist = playlistSongCrossRefRepository.hasSongInPlaylist(intent.playlistId, intent.songId)
                    if(hasSongInPlaylist == null) {
                        val repository = PlaylistSongCrossRefRepository(intent.context)
                        repository.addSongToPlaylist(intent.playlistId, intent.songId)

                        val playlistRepository = PlaylistRepository(intent.context)
                        val totalSongs = playlistRepository.getPlaylistById(intent.playlistId)
                        playlistRepository.updateTotalSongsById(
                            totalSongs.totalSongs.toInt() + 1,
                            intent.playlistId.toInt()
                        )

                        _state.value.playlists.clear()
                        _state.value.playlists.addAll(playlistRepository.getAllPlaylists())

                        sendEvent(LibraryEvent.ShowMessageLibrary("Add song to playlist success"))
                    } else{
                        sendEvent(LibraryEvent.ShowMessageLibrary("Song has in playlists"))
                    }
                }
            }
        }
    }

    private fun sendEvent(event: LibraryEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}