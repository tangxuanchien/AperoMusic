package com.example.chientx_apero.ui.player_bar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.retrofit.model.SongRetrofit
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class PlayerBarState(
    val isPlaySong: Boolean = AppCache.isPlayingSong.value
)

sealed interface PlayerBarIntent {
    data class HandleSongAction(val song: Song) : PlayerBarIntent
    data object StopSong : PlayerBarIntent
}