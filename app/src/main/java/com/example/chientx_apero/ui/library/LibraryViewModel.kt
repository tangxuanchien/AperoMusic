package com.example.chientx_apero.ui.library

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.model.PreferenceManager
import com.example.chientx_apero.retrofit.APIClient
import com.example.chientx_apero.retrofit.model.SongRetrofit
import com.example.chientx_apero.retrofit.model.toSong
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.room_db.repository.PlaylistRepository
import com.example.chientx_apero.room_db.repository.PlaylistSongCrossRefRepository
import com.example.chientx_apero.room_db.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

class LibraryViewModel() : ViewModel() {
    private val _state = MutableStateFlow<LibraryState>(LibraryState())
    val state: StateFlow<LibraryState> = _state
    private val _event = MutableSharedFlow<LibraryEvent>()
    val event: SharedFlow<LibraryEvent> = _event.asSharedFlow()

    fun processIntent(intent: LibraryIntent) {
        when (intent) {
            is LibraryIntent.LoadSongs -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val repository = SongRepository(intent.context)

                    if (intent.isLocalLibrary) {
                        val songs = repository.getAllSongsFromLocal()
                        _state.value.displayedSongs.clear()
                        _state.value.displayedSongs.addAll(songs)
                    } else {
                        _state.value.displayedSongs.clear()
                        if (PreferenceManager.getApiCalled()) {
                            _state.value.displayedSongs.clear()
                            _state.value.displayedSongs.addAll(repository.getAllSongsFromRemote())
                            Log.d("Api", "Has call")
                        } else {
                            fetchAndStoreSongsFromRemote(intent.context, repository)
                            PreferenceManager.setApiCalled()
                            Log.d("Api", "No call")
                        }
                    }
                }
            }

            is LibraryIntent.OpenMenu -> {
                var selectedSong = _state.value.selectedSong
                var expanded = _state.value.expanded

                if (selectedSong == intent.song && expanded) {
                    expanded = false
                    selectedSong = null
                } else {
                    expanded = true
                    selectedSong = intent.song
                }
                _state.update {
                    it.copy(expanded = expanded, selectedSong = selectedSong)
                }
            }

            is LibraryIntent.CloseMenu -> {
                _state.update {
                    it.copy(
                        expanded = false,
                        selectedSong = null
                    )
                }
            }

            LibraryIntent.HidePopUp -> {
                _state.update {
                    it.copy(
                        expanded = false
                    )
                }
            }

            is LibraryIntent.LoadPlaylists -> {
                viewModelScope.launch {
                    val repository = PlaylistRepository(intent.context)
                    _state.value.playlists.clear()
                    _state.value.playlists.addAll(repository.getAllPlaylists())
                }
            }

            is LibraryIntent.AddSongToPlaylist -> {
                viewModelScope.launch {
                    val playlistSongCrossRefRepository =
                        PlaylistSongCrossRefRepository(intent.context)
                    val hasSongInPlaylist = playlistSongCrossRefRepository.hasSongInPlaylist(
                        intent.playlistId,
                        intent.songId
                    )
                    if (hasSongInPlaylist == null) {
                        val repository = PlaylistSongCrossRefRepository(intent.context)
                        repository.addSongToPlaylist(intent.playlistId, intent.songId)

                        val playlistRepository = PlaylistRepository(intent.context)
                        val totalSongs = playlistRepository.getPlaylistById(intent.playlistId)
                        playlistRepository.updateTotalSongsById(
                            totalSongs.totalSongs.toInt() + 1,
                            intent.playlistId.toInt()
                        )

                        _state.value.playlists.clear()
                        _state.value.playlists.addAll(playlistRepository.getAllPlaylists())

                        sendEvent(LibraryEvent.ShowMessageLibrary("Add song to playlist success"))
                    } else {
                        sendEvent(LibraryEvent.ShowMessageLibrary("Song has in playlists"))
                    }
                }
            }
        }
    }

    private fun sendEvent(event: LibraryEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun fetchAndStoreSongsFromRemote(context: Context, repository: SongRepository) {
        val call = APIClient.build().getSongs()
//                        Call enqueue run on main thread
        call.enqueue(object : Callback<List<SongRetrofit>> {
            override fun onResponse(
                call: Call<List<SongRetrofit>>,
                response: Response<List<SongRetrofit>>,
            ) {

                if (response.isSuccessful) {
                    val songs = response.body() ?: emptyList()
                    viewModelScope.launch(Dispatchers.IO) {
//                                        Download song must run on IO thread
                        songs.map { song ->
                            val url = URL(song.path)
                            val outputFile =
                                File(context.filesDir, "${song.title}.mp3")

                            url.openStream().use { inputStream ->
                                FileOutputStream(outputFile).use { outputStream ->
                                    inputStream.copyTo(outputStream)
                                }
                            }
                            song.path = outputFile.absolutePath
                        }
                        repository.insertSongs(songs.map {
                            it.toSong(context)
                        })
                        _state.value.displayedSongs.clear()
                        _state.value.displayedSongs.addAll(repository.getAllSongsFromRemote())
                    }
                }
            }

            override fun onFailure(
                call: Call<List<SongRetrofit>>,
                t: Throwable,
            ) {
                _state.value.displayedSongs.clear()
            }
        })
    }
}