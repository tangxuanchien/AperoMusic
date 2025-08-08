package com.example.chientx_apero.ui.library

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.library.components.ButtonSelectLibrary
import com.example.chientx_apero.ui.library.components.ItemLibrary
import com.example.chientx_apero.ui.library.components.LoadingAnimation
import com.example.chientx_apero.ui.library.components.NoInternetScreen
import com.example.chientx_apero.ui.library.components.PopupAddToPlaylist
import com.example.chientx_apero.ui.library.components.shareDataToDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LibraryScreen(
    onClickBack: () -> Unit,
    onClickPlaylist: () -> Unit,
    isLibraryScreen: Boolean = false,
    viewModel: LibraryViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var isShowPopup by remember { mutableStateOf(false) }
    var isLocalLibrary by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }
    var isPlaySong by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadPlaylists(context))

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
        viewModel.processIntent(LibraryIntent.LoadSongs(context, isLocalLibrary))
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
                                        LibraryIntent.LoadSongs(
                                            context,
                                            isLocalLibrary
                                        )
                                    )
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(1000)
                                        isLoading = false
                                    }
                                },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            LazyColumn {
                                items(state.displayedSongs) { song ->
                                    val isExpanded = state.expanded && state.selectedSong == song
                                    ItemLibrary(
                                        song = song,
                                        expanded = isExpanded,
                                        isPlaySong = isPlaySong,
                                        onOpenMenu = {
                                            viewModel.processIntent(LibraryIntent.OpenMenu(song))
                                        },
                                        onDismissRequest = {
                                            viewModel.processIntent(LibraryIntent.CloseMenu)
                                        },
                                        onClick = {
                                            viewModel.processIntent(LibraryIntent.HidePopUp)
                                            isShowPopup = true
                                        },
                                        onShare = {
                                            shareDataToDevice(context, song)
                                        },
                                        onClickPlay = {
//                                            isPlaySong = !isPlaySong
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = ImageVector.Companion.vectorResource(R.drawable.play_fill),
                                contentDescription = "Next",
                                modifier = Modifier.Companion
                                    .size(24.dp)
                                    .clickable {
//                                        onClickPlay()
                                    },
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
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
                            context = context,
                            songId = state.selectedSong?.id!!,
                            playlistId = playlist.toLong()
                        )
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
        onClickBack = {},
        onClickPlaylist = {},
        isLibraryScreen = true
    )
}