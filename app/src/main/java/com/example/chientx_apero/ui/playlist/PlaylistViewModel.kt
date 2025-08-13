package com.example.chientx_apero.ui.playlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.repository.PlaylistRepository
import com.example.chientx_apero.room_db.repository.PlaylistSongCrossRefRepository
import com.example.chientx_apero.room_db.repository.SongRepository
import com.example.chientx_apero.ui.playlist.components.getAllMp3Files
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                viewModelScope.launch {
                    val repository = PlaylistSongCrossRefRepository(intent.context)
                    repository.deleteSongInPlaylistId(intent.playlistId, intent.songId)
                    loadSongsInPlaylist(intent.context, intent.playlistId)
                }
                _state.update {
                    it.copy(
                        selectedSong = null,
                        expanded = false
                    )
                }
            }

            is PlaylistIntent.LoadSongsInPlaylist -> {
                loadSongsInPlaylist(intent.context, intent.playlistId)
            }
        }
    }

    private fun loadSongsInPlaylist(context: Context, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val repository = SongRepository(context)
            val songsInPlaylist = repository.getAllSongsInPlaylist(playlistId)
            _state.value.songs.clear()
            _state.value.songs.addAll(songsInPlaylist)
        }
    }
}