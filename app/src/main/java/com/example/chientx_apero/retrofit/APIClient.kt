package com.example.chientx_apero.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.getValue

object APIClient {
    private const val BASE_URL = "https://static.apero.vn"
    private const val REQUEST_TIMEOUT = 30L
    private val retrofit by lazy { buildRetrofit() }
    fun build(): ApiService { return retrofit.create(ApiService::class.java)}

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}

object APIClientHome {
    private const val BASE_URL = "https://ws.audioscrobbler.com"
    private const val REQUEST_TIMEOUT = 30L
    private val retrofit by lazy { buildRetrofit() }
    fun build(): ApiServiceHome { return retrofit.create(ApiServiceHome::class.java)}

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}