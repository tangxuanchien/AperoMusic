package com.example.chientx_apero.ui.my_playlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPlaylistViewModel : ViewModel() {
    private val _state = MutableStateFlow<MyPlaylistState>(MyPlaylistState())
    val state: StateFlow<MyPlaylistState> = _state
    private val _event = MutableSharedFlow<MyPlaylistEvent>()
    val event: SharedFlow<MyPlaylistEvent> = _event.asSharedFlow()

    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.OpenMenu -> {
                var selectedMyPlaylist = _state.value.selectedMyPlaylist
                var expanded = _state.value.expanded

                if (selectedMyPlaylist == intent.playlist && expanded) {
                    expanded = false
                    selectedMyPlaylist = null
                } else {
                    expanded = true
                    selectedMyPlaylist = intent.playlist
                }
                _state.update {
                    it.copy(expanded = expanded, selectedMyPlaylist = selectedMyPlaylist)
                }
            }

            is MyPlaylistIntent.CloseMenu -> {
                _state.update {
                    it.copy(
                        expanded = false,
                        selectedMyPlaylist = null
                    )
                }
            }

            is MyPlaylistIntent.LoadPlaylists -> {
                loadPlaylists(intent.context)
            }

            is MyPlaylistIntent.RemovePlaylist -> {
                viewModelScope.launch {
                    val repository = PlaylistRepository(intent.context)
                    repository.deletePlaylist(
                        intent.playlist.id
                    )
                    loadPlaylists(intent.context)
                }

                _state.update {
                    it.copy(
                        selectedMyPlaylist = null,
                        expanded = false
                    )
                }
            }

            is MyPlaylistIntent.SubmitNewMyPlaylist -> {
                if (!intent.titleMyPlaylist.isEmpty()) {
                    viewModelScope.launch {
                        val repository = PlaylistRepository(intent.context)
                        repository.insertPlaylist(
                            Playlist(
                                id = 0,
                                name = intent.titleMyPlaylist,
                                image = null,
                                totalSongs = 0
                            )
                        )
                        loadPlaylists(intent.context)
                    }
                    sendEvent(MyPlaylistEvent.ShowMessageMyPlaylist("Create playlist ${intent.titleMyPlaylist} success"))
                } else {
                    sendEvent(MyPlaylistEvent.ShowMessageMyPlaylist("Title cannot be empty!"))
                }
            }

            is MyPlaylistIntent.RenamePlaylist -> {
                val index =
                    _state.value.displayedMyPlaylists.indexOfFirst { it.id == intent.playlist.id }
                _state.update {
                    it.copy(
                        expanded = false,
                        oldNameMyPlaylist = _state.value.displayedMyPlaylists[index].name
                    )
                }
            }

            is MyPlaylistIntent.SubmitRenameMyPlaylist -> {
                if (intent.oldNameMyPlaylist.isNotEmpty()) {
                    viewModelScope.launch {
                        val repository = PlaylistRepository(intent.context)
                        repository.updatePlaylistName(
                            id = intent.playlist.id,
                            name = intent.oldNameMyPlaylist
                        )
                        loadPlaylists(intent.context)
                        sendEvent(MyPlaylistEvent.ShowMessageMyPlaylist("Update playlist name success"))
                    }
                } else {
                    sendEvent(MyPlaylistEvent.ShowMessageMyPlaylist("Playlist name cannot empty"))
                }
            }

            is MyPlaylistIntent.OldNameMyPlaylistChanged -> {
                _state.update {
                    it.copy(
                        oldNameMyPlaylist = intent.oldNameMyPlaylist
                    )
                }
            }
        }
    }

    private fun sendEvent(event: MyPlaylistEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun loadPlaylists(context: Context) {
        viewModelScope.launch {
            val repository = PlaylistRepository(context)
            _state.value.displayedMyPlaylists.clear()
            _state.value.displayedMyPlaylists.addAll(repository.getAllPlaylists())
        }
    }
}