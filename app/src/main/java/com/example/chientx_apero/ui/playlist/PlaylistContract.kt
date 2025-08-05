package com.example.chientx_apero.ui.playlist

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class PlaylistState(
    val songs: SnapshotStateList<Song> = mutableStateListOf(),
    val playlists: Playlist? = null,
    val isGridView: Boolean = false,
    val isSortView: Boolean = false,
    val expanded: Boolean = false,
    val selectedSong: Song? = null,
    val currentTheme: ThemeData = darkTheme,
    val checked: Boolean = false,
)

sealed interface PlaylistIntent {
    data object ToggleSortView : PlaylistIntent
    data object ToggleGridView : PlaylistIntent
    data class RemoveSong(val context: Context, val playlistId: Long, val songId: Long) : PlaylistIntent
    data class OpenMenu(val song: Song) : PlaylistIntent
    data object CloseMenu : PlaylistIntent
    data class LoadSongsInPlaylist(val context: Context, val playlistId: Long): PlaylistIntent
}