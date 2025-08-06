package com.example.chientx_apero.ui.library

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.retrofit.model.SongRetrofit
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class LibraryState(
    val displayedSongs: SnapshotStateList<Song> = mutableStateListOf(),
    val songsFromRemote: SnapshotStateList<SongRetrofit> = mutableStateListOf(),
    val playlists: SnapshotStateList<Playlist> = mutableStateListOf(),
    val currentTheme: ThemeData = darkTheme,
    val expanded: Boolean = false,
    val selectedSong: Song? = null
)

sealed interface LibraryIntent {
    data class LoadSongs(val context: Context, val isLocalLibrary: Boolean) : LibraryIntent
    data class OpenMenu(val song: Song) : LibraryIntent
    data object CloseMenu : LibraryIntent
    data object HidePopUp : LibraryIntent
    data class LoadPlaylists(val context: Context) : LibraryIntent
    data class AddSongToPlaylist(val context: Context, val songId: Long, val playlistId: Long) :
        LibraryIntent
}

sealed interface LibraryEvent {
    data class ShowMessageLibrary(val message: String): LibraryEvent
}