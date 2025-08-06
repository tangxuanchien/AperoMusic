package com.example.chientx_apero.retrofit

import com.example.chientx_apero.retrofit.model.SongRetrofit
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("techtrek/Remote_audio.json")
    fun getSongs() : Call<List<SongRetrofit>>
}