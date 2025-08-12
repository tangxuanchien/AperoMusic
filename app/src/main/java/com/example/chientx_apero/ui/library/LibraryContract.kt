package com.example.chientx_apero.ui.library

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
    val selectedSong: Song? = null,
    val isPlaySong: Boolean = false
)

sealed interface LibraryIntent {
    data class LoadSongs(val isLocalLibrary: Boolean) : LibraryIntent
    data class OpenMenu(val song: Song) : LibraryIntent
    data object CloseMenu : LibraryIntent
    data object HidePopUp : LibraryIntent
    data class HandleSongAction(val song: Song) : LibraryIntent
    data object StopSong : LibraryIntent
    data object LoadPlaylists : LibraryIntent
    data class AddSongToPlaylist(val songId: Long, val playlistId: Long) :
        LibraryIntent
}

sealed interface LibraryEvent {
    data class ShowMessageLibrary(val message: String) : LibraryEvent
}