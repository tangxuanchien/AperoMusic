package com.example.chientx_apero.ui.home

import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.TrackRetrofit
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

data class HomeState(
    val topArtists: List<ArtistRetrofit>? = null,
    val topTracks: List<TrackRetrofit>? = null,
    val topAlbums: List<AlbumRetrofit>? = null
)

sealed interface HomeIntent {
    data object LoadData : HomeIntent
}

sealed interface HomeEvent {
    data class showHomeEvent(val message: String): HomeEvent
}