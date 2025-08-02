package com.example.chientx_apero.library_screen

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.information_screen.InformationIntent
import com.example.chientx_apero.model.Song
import com.example.chientx_apero.playlist_screen.PlaylistIntent
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class LibraryState(
    val displayedSongs: SnapshotStateList<Song> = mutableStateListOf(),
    val currentTheme: ThemeData = darkTheme,
    val expanded: Boolean = false,
    val selectedSong: Song? = null,
)

sealed interface LibraryIntent {
    data class LoadSongs(val context: Context): LibraryIntent
    data class OpenMenu(val song: Song) : LibraryIntent
    data object CloseMenu : LibraryIntent
    data object HidePopUp : LibraryIntent
}