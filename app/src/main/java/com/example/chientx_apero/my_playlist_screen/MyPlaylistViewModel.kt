package com.example.chientx_apero.my_playlist_screen

import androidx.lifecycle.ViewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.model.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyPlaylistViewModel : ViewModel() {
    private val _state = MutableStateFlow<MyPlaylistState>(MyPlaylistState())
    val state: StateFlow<MyPlaylistState> = _state

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

            is MyPlaylistIntent.RemovePlaylist -> {
                _state.value.displayedMyPlaylists.remove(intent.playlist)
                _state.update {
                    it.copy(
                        selectedMyPlaylist = null,
                        expanded = false
                    )
                }
            }
            is MyPlaylistIntent.SubmitNewMyPlaylist -> {
                if (!intent.titleMyPlaylist.isEmpty()) {
                    val index: Int = _state.value.displayedMyPlaylists.size + 1
                    _state.value.displayedMyPlaylists.add(
                        Playlist(
                            index, intent.titleMyPlaylist, R.drawable.justin_3
                        )
                    )
                }
            }

            is MyPlaylistIntent.RenamePlaylist -> {
                val index = _state.value.displayedMyPlaylists.indexOfFirst { it.id == intent.playlist.id }
                _state.update {
                    it.copy(
                        expanded = false,
                        oldNameMyPlaylist = _state.value.displayedMyPlaylists[index].name
                    )
                }
            }

            is MyPlaylistIntent.SubmitRenameMyPlaylist -> {
                val index = _state.value.displayedMyPlaylists.indexOfFirst { it.id == intent.playlist.id }
                _state.value.displayedMyPlaylists[index] = intent.playlist.copy(
                    name = intent.oldNameMyPlaylist
                )
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
}