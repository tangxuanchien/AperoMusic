package com.example.chientx_apero.retrofit.model

import com.google.gson.annotations.SerializedName

data class TrackRetrofit(
    val name: String,
    val playcount: String,
    val listeners: String,
    val url: String,
    val artist: ArtistInfo,
    val streamable: String,
    val image: List<TrackImage>,
    @SerializedName("@attr")
    val attr: Attribute
)

data class Attribute(
    val rank: String
)

data class TrackImage(
    @SerializedName("#text")
    val url: String,
    val size: String
)

data class ArtistInfo(
    val name: String,
    val mbid: String,
    val url: String
)

data class TopTracksResponse(
    @SerializedName("toptracks")
    val topTracks: TracksWrapper
)

data class TracksWrapper(
    val track: List<TrackRetrofit>
)