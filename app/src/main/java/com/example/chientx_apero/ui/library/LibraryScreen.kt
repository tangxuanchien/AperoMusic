package com.example.chientx_apero.ui.library

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.library.components.ItemLibrary
import com.example.chientx_apero.ui.library.components.PopupAddToPlaylist
import com.example.chientx_apero.ui.library.components.shareDataToDevice


@Composable
fun LibraryScreen(
    onClickBack: () -> Unit,
    onClickPlaylist: () -> Unit,
    isLibraryScreen: Boolean = false,
    viewModel: LibraryViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var isShowPopup by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.processIntent(LibraryIntent.LoadSongs(context))
        viewModel.processIntent(LibraryIntent.LoadPlaylists(context))
        viewModel.event.collect { event ->
            when(event) {
                is LibraryEvent.ShowMessageLibrary -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                    .padding(vertical = 18.dp, horizontal = 10.dp)
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
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Local",
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Remote",
                        )
                    }
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    LazyColumn {
                        items(state.displayedSongs) { song ->
                            val isExpanded = state.expanded && state.selectedSong == song
                            ItemLibrary(
                                song = song,
                                expanded = isExpanded,
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
                                }
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
        if(isShowPopup){
            PopupAddToPlaylist(
                onClickCreatePlaylist = onClickPlaylist,
                onClickAddToPlaylist = { playlist ->
                    viewModel.processIntent(LibraryIntent.AddSongToPlaylist(
                        context = context,
                        songId = state.selectedSong?.id!!.toLong(),
                        playlistId = playlist.toLong()
                    ))
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
        onClickPlaylist = {}
    )
}