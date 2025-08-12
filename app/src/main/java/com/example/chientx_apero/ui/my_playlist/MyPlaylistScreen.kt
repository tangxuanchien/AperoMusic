package com.example.chientx_apero.ui.my_playlist

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.my_playlist.components.DefaultMyPlaylistScreen
import com.example.chientx_apero.ui.my_playlist.components.ItemMyPlaylist
import com.example.chientx_apero.ui.my_playlist.components.PopupAddMyPlaylist
import com.example.chientx_apero.ui.player_bar.PlayerBarScreen
import com.example.chientx_apero.ui.player_bar.PlayerBarViewModel
import com.example.chientx_apero.ui.playlist.PlaylistIntent
import com.example.chientx_apero.ui.playlist.components.HeaderPlaylist

@Composable
fun MyPlaylistScreen(
    isPlaylistScreen: Boolean = false,
    viewModel: MyPlaylistViewModel = viewModel(),
    onClickLibrary: () -> Unit = {},
    onClickPlaylist: () -> Unit = {},
    onClickHome: () -> Unit = {},
    playerBarViewModel: PlayerBarViewModel = viewModel()
) {
    var titleMyPlaylist by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    var isRename by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.processIntent(MyPlaylistIntent.LoadPlaylists(context))
        viewModel.event.collect { event ->
            when (event) {
                is MyPlaylistEvent.ShowMessageMyPlaylist -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    MaterialTheme(
        colorScheme = state.currentTheme.color
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 18.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
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
                                            MyPlaylistIntent.RemovePlaylist(playlist, context)
                                        )
                                    },
                                    onClickRename = {
                                        viewModel.processIntent(
                                            MyPlaylistIntent.RenamePlaylist(playlist, context)
                                        )
                                        showPopup = true
                                        isRename = true
                                    },
                                    onClickPlaylist = {
                                        onClickPlaylist()
                                        AppCache.playlist = playlist
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
                if (AppCache.playingSong != null) {
                    PlayerBarScreen(
                        viewModel = playerBarViewModel
                    )
                }
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
                                        oldNameMyPlaylist = state.oldNameMyPlaylist,
                                        context
                                    )
                                )
                                showPopup = false
                                isRename = false
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
                                        titleMyPlaylist, context
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                if (AppCache.playingSong != null) {
                    PlayerBarScreen(
                        viewModel = playerBarViewModel
                    )
                }
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    onClickPlaylist = onClickPlaylist,
                    onClickLibrary = onClickLibrary,
                    onClickHome = onClickHome,
                    isPlaylistScreen = isPlaylistScreen
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewItemGrid() {
    MyPlaylistScreen()
}

