package com.example.chientx_apero.ui.library

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.library.components.ButtonSelectLibrary
import com.example.chientx_apero.ui.library.components.ItemLibrary
import com.example.chientx_apero.ui.library.components.LoadingAnimation
import com.example.chientx_apero.ui.library.components.NoInternetScreen
import com.example.chientx_apero.ui.library.components.PlayerBar
import com.example.chientx_apero.ui.library.components.PopupAddToPlaylist
import com.example.chientx_apero.ui.library.components.shareDataToDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LibraryScreen(
    onClickBack: () -> Unit,
    onClickPlayer: () -> Unit,
    onClickPlaylist: () -> Unit,
    isLibraryScreen: Boolean = false,
    viewModel: LibraryViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var isShowPopup by remember { mutableStateOf(false) }
    var isLocalLibrary by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadPlaylists, context)

        launch {
            viewModel.event.collect { event ->
                when (event) {
                    is LibraryEvent.ShowMessageLibrary -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    LaunchedEffect(isLocalLibrary) {
        viewModel.processIntent(LibraryIntent.LoadSongs(isLocalLibrary), context)
        delay(1500)
        isLoading = false
    }
    MaterialTheme(
        colorScheme = state.currentTheme.color
    ) {
        Box(
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 18.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Library",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Companion.Center,
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    ButtonSelectLibrary(
                        onClickSelectLibrary = {
                            if (!isLocalLibrary) {
                                isLocalLibrary = true
                                isLoading = true
                            }
                        },
                        isLocalLibrary = isLocalLibrary,
                        text = "Local"
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    ButtonSelectLibrary(
                        onClickSelectLibrary = {
                            if (isLocalLibrary) {
                                isLocalLibrary = false
                                isLoading = true
                            }
                        },
                        isLocalLibrary = !isLocalLibrary,
                        text = "Remote"
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (isLoading) {
                        LoadingAnimation()
                    } else {
                        if (state.displayedSongs.isEmpty()) {
                            NoInternetScreen(
                                onClickTryAgain = {
                                    isLoading = true
                                    viewModel.processIntent(
                                        LibraryIntent.LoadSongs(isLocalLibrary), context
                                    )
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(1000)
                                        isLoading = false
                                    }
                                },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            Log.d("LIB", "LibraryScreen: ${AppCache.playingSong}")
                            LazyColumn {
                                items(state.displayedSongs) { song ->
                                    Log.d("LIB", "LibraryScreen: ${AppCache.playingSong == song}")
                                    ItemLibrary(
                                        song = song,
                                        expanded = state.expanded && state.selectedSong == song,
                                        isPlaySong = AppCache.playingSong?.id == song.id,
                                        onOpenMenu = {
                                            viewModel.processIntent(
                                                LibraryIntent.OpenMenu(song),
                                                context
                                            )
                                        },
                                        onDismissRequest = {
                                            viewModel.processIntent(
                                                LibraryIntent.CloseMenu,
                                                context
                                            )
                                        },
                                        onClick = {
                                            viewModel.processIntent(
                                                LibraryIntent.HidePopUp,
                                                context
                                            )
                                            isShowPopup = true
                                        },
                                        onShare = {
                                            shareDataToDevice(context, song)
                                        },
                                        onClickPlay = {
                                            viewModel.processIntent(
                                                LibraryIntent.HandleSongAction(song),
                                                context
                                            )
                                            AppCache.playingSong = song
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                if (state.selectedSong != null) {
                    PlayerBar(
                        song = state.selectedSong!!,
                        isPlaySong = state.isPlaySong,
                        onClickPlaySong = {
                            viewModel.processIntent(LibraryIntent.HandleSongAction(state.selectedSong!!), context)
                        },
                        onClickPlayer = {
                            onClickPlayer()
                            AppCache.playingSong = state.selectedSong
                        }
                    )
                }
                NavigationBar(
                    onClickPlaylist = onClickPlaylist,
                    onClickLibrary = { },
                    isLibraryScreen = isLibraryScreen
                )
            }
        }
        if (isShowPopup) {
            PopupAddToPlaylist(
                onClickCreatePlaylist = onClickPlaylist,
                onClickAddToPlaylist = { playlist ->
                    viewModel.processIntent(
                        LibraryIntent.AddSongToPlaylist(
                            songId = state.selectedSong?.id!!,
                            playlistId = playlist.toLong()
                        ),
                        context
                    )
                    isShowPopup = false
                },
                onDismissRequest = {
                    isShowPopup = !isShowPopup
                },
                playlists = state.playlists
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LibraryScreen(
        onClickPlayer = {},
        onClickPlaylist = {},
        isLibraryScreen = true,
        onClickBack = {}
    )
}