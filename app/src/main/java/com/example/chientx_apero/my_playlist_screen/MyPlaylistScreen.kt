package com.example.chientx_apero.my_playlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.home_screen.NavigationBar
import com.example.chientx_apero.model.DataPlaylists
import com.example.chientx_apero.model.MyPlaylists
import com.example.chientx_apero.model.Playlist
import com.example.chientx_apero.playlist_screen.HeaderPlaylist

@Composable
fun MyPlaylistScreen(
    isPlaylistScreen: Boolean = false,
    viewModel: MyPlaylistViewModel = viewModel(),
    onClickLibrary: () -> Unit = {},
    onClickPlaylist: () -> Unit = {}
) {
    var titleMyPlaylist by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    var isRename by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    MaterialTheme(
        colorScheme = state.currentTheme.color
    ) {
        Box(
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 18.dp, horizontal = 10.dp)
                    .fillMaxSize()
            ) {
                HeaderPlaylist(
                    stateMyPlaylist = true,
                    onClickAddMyPlaylist = {
                        titleMyPlaylist = ""
                        showPopup = true
                    }
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (!state.displayedMyPlaylists.isEmpty()) {
                        LazyColumn {
                            items(state.displayedMyPlaylists) { playlist ->
                                val isExpanded =
                                    state.expanded && state.selectedMyPlaylist == playlist
                                ItemMyPlaylist(
                                    playlist = playlist,
                                    expanded = isExpanded,
                                    onOpenMenu = {
                                        viewModel.processIntent(MyPlaylistIntent.OpenMenu(playlist))
                                    },
                                    onDismissRequest = {
                                        viewModel.processIntent(MyPlaylistIntent.CloseMenu)
                                    },
                                    onClickRemovePlaylist = {
                                        viewModel.processIntent(
                                            MyPlaylistIntent.RemovePlaylist(playlist)
                                        )
                                    },
                                    onClickRename = {
                                        viewModel.processIntent(
                                            MyPlaylistIntent.RenamePlaylist(playlist)
                                        )
                                        showPopup = true
                                        isRename = true
                                    },
                                    onClickPlaylist = {
                                        onClickPlaylist()
                                        DataPlaylists.name = playlist.name
                                        DataPlaylists.songs = playlist.song
                                        DataPlaylists.id = playlist.id
                                    }
                                )
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (!showPopup) {
                                DefaultMyPlaylistScreen(
                                    onClickAddMyPlaylist = {
                                        showPopup = true
                                        titleMyPlaylist = ""
                                    }
                                )
                            }
                        }
                    }
                }
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    onClickPlaylist = {},
                    onClickLibrary = {
                        onClickLibrary()
                        MyPlaylists.myPlaylists = state.displayedMyPlaylists
                    },
                    isPlaylistScreen = isPlaylistScreen
                )
                if (showPopup) {
                    if (isRename) {
                        PopupAddMyPlaylist(
                            onDismissRequest = {
                                showPopup = false
                                titleMyPlaylist = ""
                            },
                            onCreateMyPlaylist = {
                                viewModel.processIntent(
                                    MyPlaylistIntent.SubmitRenameMyPlaylist(
                                        playlist = state.selectedMyPlaylist!!,
                                        oldNameMyPlaylist = state.oldNameMyPlaylist
                                    )
                                )
                                showPopup = false
                            },
                            onValueChange = {
                                viewModel.processIntent(
                                    MyPlaylistIntent.OldNameMyPlaylistChanged(it)
                                )
                            },
                            value = state.oldNameMyPlaylist
                        )
                    } else {
                        PopupAddMyPlaylist(
                            onDismissRequest = {
                                showPopup = false
                            },
                            onCreateMyPlaylist = {
                                viewModel.processIntent(
                                    MyPlaylistIntent.SubmitNewMyPlaylist(
                                        titleMyPlaylist
                                    )
                                )
                                showPopup = false
                            },
                            onValueChange = {
                                titleMyPlaylist = it
                            },
                            value = titleMyPlaylist
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewItemGrid() {
    MyPlaylistScreen()
}

