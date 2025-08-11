package com.example.chientx_apero.retrofit.model

import com.google.gson.annotations.SerializedName

data class AlbumRetrofit(
    val name: String,
    val playcount: String,
    val listeners: String,
    val url: String,
    val artist: ArtistInfo,
    val streamable: String,
    val image: List<AlbumImage>,
    @SerializedName("@attr")
    val attr: Attribute
)

data class AlbumImage(
    @SerializedName("#text")
    val url: String,
    val size: String
)

data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topAlbums: AlbumsWrapper
)

data class AlbumsWrapper(
    val album: List<AlbumRetrofit>
)