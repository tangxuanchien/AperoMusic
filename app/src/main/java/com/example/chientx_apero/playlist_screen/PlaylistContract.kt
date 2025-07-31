package com.example.chientx_apero.playlist_screen

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.chientx_apero.model.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class PlaylistState(
    val displayedSongs: SnapshotStateList<Song> = mutableStateListOf(),
    val isGridView: Boolean = false,
    val isSortView: Boolean = false,
    val expanded: Boolean = false,
    val selectedSong: Song? = null,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false
)

sealed interface PlaylistIntent {
    data object ToggleSortView : PlaylistIntent
    data object ToggleGridView : PlaylistIntent
    data class RemoveSong(val song: Song) : PlaylistIntent
    data class OpenMenu(val song: Song) : PlaylistIntent
    data object CloseMenu : PlaylistIntent
    data class LoadSongs(val context: Context): PlaylistIntent
}

sealed interface PlaylistEvent {
//    data class LoadSongs(val context: Context): PlaylistEvent
}