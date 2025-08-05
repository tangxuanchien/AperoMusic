package com.example.chientx_apero.ui.my_playlist

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class MyPlaylistState(
    val displayedMyPlaylists: SnapshotStateList<Playlist> = mutableStateListOf<Playlist>(),
    val expanded: Boolean = false,
    val selectedMyPlaylist: Playlist? = null,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false,
    val oldNameMyPlaylist: String = "",
)

sealed interface MyPlaylistIntent {
    data class RemovePlaylist(val playlist: Playlist, val context: Context) : MyPlaylistIntent
    data class RenamePlaylist(val playlist: Playlist, val context: Context) : MyPlaylistIntent
    data class OpenMenu(val playlist: Playlist) : MyPlaylistIntent
    data object CloseMenu : MyPlaylistIntent
    data class OldNameMyPlaylistChanged(val oldNameMyPlaylist: String) : MyPlaylistIntent
    data class SubmitNewMyPlaylist(val titleMyPlaylist: String, val context: Context): MyPlaylistIntent
    data class LoadPlaylists(val context: Context): MyPlaylistIntent
    data class SubmitRenameMyPlaylist(val playlist: Playlist, val oldNameMyPlaylist: String, val context: Context): MyPlaylistIntent
}

sealed interface MyPlaylistEvent {
    data class ShowMessageMyPlaylist(val message: String): MyPlaylistEvent
}