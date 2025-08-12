package com.example.chientx_apero.model

import android.net.Uri
import androidx.core.net.toUri
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.TrackRetrofit
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.room_db.entity.User

object AppCache {
    var playlist: Playlist? = null
    var currentUser: User? = null
    var playingSong: Song? = null

    var topArtists: List<ArtistRetrofit>? = null
    var topAlbums: List<AlbumRetrofit>? = null
    var topTracks: List<TrackRetrofit>? = null
}