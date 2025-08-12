package com.example.chientx_apero.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.model.PreferenceManager
import com.example.chientx_apero.retrofit.APIClientHome
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.TopAlbumsResponse
import com.example.chientx_apero.retrofit.model.TopArtistsResponse
import com.example.chientx_apero.retrofit.model.TopTracksResponse
import com.example.chientx_apero.room_db.AppDatabase
import com.example.chientx_apero.room_db.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class HomeViewModel() : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState> = _state
    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> = _event.asSharedFlow()

    fun processIntent(intent: HomeIntent, context: Context) {
        val current = _state.value
        when (intent) {
            HomeIntent.LoadData -> {
                viewModelScope.launch {
                    val userId = PreferenceManager.getSaveUserId()
                    val repository = UserRepository(context)
                    AppCache.currentUser = repository.getUserByIds(userId!!)
                    fetchTopArtistsFromApi()
                    fetchTopTracksFromApi()
                    fetchTopAlbumsFromApi()
                }
            }
        }
    }

    fun fetchTopArtistsFromApi() {
        val call = APIClientHome.build().getTopArtists()
        call.enqueue(object : Callback<TopArtistsResponse> {
            override fun onResponse(
                call: Call<TopArtistsResponse>,
                response: Response<TopArtistsResponse>,
            ) {
                if (response.isSuccessful) {
                    val topArtists = response.body()?.artists?.artist
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(
                                topArtists = topArtists
                            )
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<TopArtistsResponse>,
                t: Throwable,
            ) {
            }
        })
    }

    fun fetchTopAlbumsFromApi() {
        val call = APIClientHome.build().getTopAlbums()
        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse>,
                response: Response<TopAlbumsResponse>,
            ) {
                if (response.isSuccessful) {
                    val topAlbums = response.body()?.topAlbums?.album
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(
                                topAlbums = topAlbums
                            )
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<TopAlbumsResponse>,
                t: Throwable,
            ) {
                Log.d("TopAlbums", "onFailure: $t")
            }
        })
    }

    fun fetchTopTracksFromApi() {
        val call = APIClientHome.build().getTopTracks()
        call.enqueue(object : Callback<TopTracksResponse> {
            override fun onResponse(
                call: Call<TopTracksResponse>,
                response: Response<TopTracksResponse>,
            ) {
                if (response.isSuccessful) {
                    val topTracks = response.body()?.topTracks?.track
                    Log.d("TopTracks", "true")
                    viewModelScope.launch(Dispatchers.IO) {
                        _state.update {
                            it.copy(
                                topTracks = topTracks
                            )
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<TopTracksResponse>,
                t: Throwable,
            ) {
                Log.d("TopTracks", "$t")
            }
        })
    }

    private fun sendEvent(event: HomeEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}