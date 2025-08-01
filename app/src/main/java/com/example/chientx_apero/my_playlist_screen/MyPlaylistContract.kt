package com.example.chientx_apero.my_playlist_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.model.Playlist
import com.example.chientx_apero.model.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class MyPlaylistState(
    val displayedMyPlaylists: SnapshotStateList<Playlist> = mutableStateListOf(),
    val expanded: Boolean = false,
    val selectedMyPlaylist: Playlist? = null,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false,
    val showPopup: Boolean = false,
    val titleMyPlaylist: String = ""
)

sealed interface MyPlaylistIntent {
    data class RemovePlaylist(val playlist: Playlist) : MyPlaylistIntent
    data class OpenMenu(val playlist: Playlist) : MyPlaylistIntent
    data object CloseMenu : MyPlaylistIntent
    data object AddMyPlaylist : MyPlaylistIntent
    data class TitleMyPlaylistChanged(val titleMyPlaylist: String): MyPlaylistIntent
}