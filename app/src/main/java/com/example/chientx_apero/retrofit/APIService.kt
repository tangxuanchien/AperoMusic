package com.example.chientx_apero.retrofit

import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.SongRetrofit
import com.example.chientx_apero.retrofit.model.TopAlbumsResponse
import com.example.chientx_apero.retrofit.model.TopArtistsResponse
import com.example.chientx_apero.retrofit.model.TopTracksResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("techtrek/Remote_audio.json")
    fun getSongs() : Call<List<SongRetrofit>>
}

interface ApiServiceHome {
    @GET("2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=chart.gettopartists")
    fun getTopArtists() : Call<TopArtistsResponse>
    @GET("2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=artist.getTopTracks&mbid=f9b593e6-4503-414c-99a0-46595ecd2e23")
    fun getTopTracks() : Call<TopTracksResponse>
    @GET("2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=artist.getTopAlbums&mbid=f9b593e6-4503-414c-99a0-46595ecd2e23")
    fun getTopAlbums() : Call<TopAlbumsResponse>
}