package com.example.chientx_apero.my_playlist_screen

import com.example.chientx_apero.playlist_screen.PlaylistIntent
import com.example.chientx_apero.playlist_screen.PlaylistState
import com.example.chientx_apero.playlist_screen.getAllMp3Files
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.Song
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

            MyPlaylistIntent.AddMyPlaylist -> {
                _state.update {
                    it.copy(
                        showPopup = true
                    )
                }
            }

            is MyPlaylistIntent.TitleMyPlaylistChanged -> {
                _state.update {
                    it.copy(
                        titleMyPlaylist = intent.titleMyPlaylist
                    )
                }
            }
        }
    }
}