package com.example.chientx_apero.ui.playlist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.SongModel
import com.example.chientx_apero.ui.playlist.components.getAllMp3Files
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
                var selectedSong = _state.value.selectedSongModel
                var expanded = _state.value.expanded

                if (selectedSong == intent.songModel && expanded) {
                    expanded = false
                    selectedSong = null
                } else {
                    expanded = true
                    selectedSong = intent.songModel
                }
                _state.update {
                    it.copy(expanded = expanded, selectedSongModel = selectedSong)
                }
            }

            is PlaylistIntent.CloseMenu -> {
                _state.update {
                    it.copy(
                        expanded = false,
                        selectedSongModel = null
                    )
                }
            }

            is PlaylistIntent.RemoveSong -> {
                _state.value.displayedSongModels.remove(intent.songModel)
                _state.update {
                    it.copy(
                        selectedSongModel = null,
                        expanded = false
                    )
                }
            }

            is PlaylistIntent.LoadSongs -> {
                val intentSongIds = intent.songModels.map { it.id }.toSet()
                val songs = getAllMp3Files(intent.context)
                    .filter {
                        it.id in intentSongIds
                    }
                val displayedSongModels = mutableStateListOf<SongModel>()
                displayedSongModels.addAll(songs)

                _state.update {
                    it.copy(
                        displayedSongModels = displayedSongModels
                    )
                }
            }
        }
    }
}