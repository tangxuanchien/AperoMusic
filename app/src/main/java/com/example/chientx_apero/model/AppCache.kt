package com.example.chientx_apero.model

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import com.example.chientx_apero.R
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.TrackRetrofit
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.room_db.entity.User
import kotlinx.coroutines.flow.MutableStateFlow

object AppCache {
    var playlist: Playlist? = null
    var currentUser: User? = null
    var locale by mutableStateOf("en")
    var language: Int = R.string.english
    var playingSong by mutableStateOf<Song?>(null)
    val isPlayingSong = MutableStateFlow(false)
    var isShowPopup = false
    var topArtists: List<ArtistRetrofit>? = null
    var topAlbums: List<AlbumRetrofit>? = null
    var topTracks: List<TrackRetrofit>? = null
}

// Fake data
val songs = mutableListOf<Song>(
    Song(1L, "Anh da on hon", "".toUri(), null, "MCK", "03:36", "Free Fire"),
    Song(2L, "Soda", "".toUri(), null, "tlinh", "03:36", "local"),
    Song(3L, "2h", "".toUri(), null, "Obito", "03:36", "local"),
    Song(4L, "Phong Ly", "".toUri(), null, "MCK", "03:36", "local"),
    Song(5L, "2323", "".toUri(), null, "MCK", "03:36", "local"),
    Song(6L, "okok", "".toUri(), null, "MCK", "03:36", "local"),
    Song(7L, "tutu", "".toUri(), null, "MCK", "03:36", "local"),
    Song(8L, "no way", "".toUri(), null, "MCK", "03:36", "local"),
    Song(9L, "no way 2", "".toUri(), null, "MCK", "03:36", "local"),
    Song(10L, "Huhu", "".toUri(), null, "MCK", "03:36", "local")
)