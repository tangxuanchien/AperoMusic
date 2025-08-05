package com.example.chientx_apero.ui.playlist

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.model.SongModel
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class PlaylistState(
    val displayedSongModels: SnapshotStateList<SongModel> = mutableStateListOf(),
    val isGridView: Boolean = false,
    val isSortView: Boolean = false,
    val expanded: Boolean = false,
    val selectedSongModel: SongModel? = null,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false
)

sealed interface PlaylistIntent {
    data object ToggleSortView : PlaylistIntent
    data object ToggleGridView : PlaylistIntent
    data class RemoveSong(val songModel: SongModel) : PlaylistIntent
    data class OpenMenu(val songModel: SongModel) : PlaylistIntent
    data object CloseMenu : PlaylistIntent
    data class LoadSongs(val context: Context, val songModels: MutableList<SongModel>): PlaylistIntent
}