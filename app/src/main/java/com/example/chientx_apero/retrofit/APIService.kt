package com.example.chientx_apero.retrofit

import com.example.chientx_apero.retrofit.model.SongRetrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("techtrek/Remote_audio.json")
    fun getSongs() : Call<List<SongRetrofit>>
}