package com.example.chientx_apero.playlist_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PlaylistViewModel : ViewModel() {
    private val _state = MutableStateFlow<PlaylistState>(PlaylistState())
    val state: StateFlow<PlaylistState> = _state

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            PlaylistIntent.ToggleGridView -> {
                _state.update {
                    it.copy(isGridView = !it.isGridView)
                }
            }

            PlaylistIntent.ToggleSortView -> {
                _state.update {
                    it.copy(isSortView = !it.isSortView)
                }
            }

            is PlaylistIntent.OpenMenu -> {
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

            is PlaylistIntent.CloseMenu -> {
                _state.update {
                    it.copy(
                        expanded = false,
                        selectedSong = null
                    )
                }
            }

            is PlaylistIntent.RemoveSong -> {
                _state.value.displayedSongs.remove(intent.song)
                _state.update {
                    it.copy(
                        selectedSong = null,
                        expanded = false
                    )
                }
            }

            is PlaylistIntent.LoadSongs -> {
                val intentSongIds = intent.songs.map { it.id }.toSet()
                val songs = getAllMp3Files(intent.context)
                    .filter {
                        it.id in intentSongIds
                    }
                var displayedSongs = mutableStateListOf<Song>()
                displayedSongs.addAll(songs)

                _state.update {
                    it.copy(
                        displayedSongs = displayedSongs
                    )
                }
            }
        }
    }
}