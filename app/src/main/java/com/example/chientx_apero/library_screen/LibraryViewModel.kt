package com.example.chientx_apero.library_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.chientx_apero.model.Song
import com.example.chientx_apero.playlist_screen.PlaylistIntent
import com.example.chientx_apero.playlist_screen.getAllMp3Files
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LibraryViewModel: ViewModel() {
    private val _state = MutableStateFlow<LibraryState>(LibraryState())
    val state: StateFlow<LibraryState> = _state

    fun processIntent(intent: LibraryIntent){
        when(intent){
            is LibraryIntent.LoadSongs -> {
                val song = getAllMp3Files(intent.context)
                _state.update {
                    it.copy(
                        displayedSongs = mutableStateListOf<Song>().apply { addAll(song) }
                    )
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
        }
    }
}