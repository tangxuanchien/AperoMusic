package com.example.chientx_apero.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.playlist.components.HeaderPlaylist
import com.example.chientx_apero.ui.playlist.components.ItemColumn
import com.example.chientx_apero.ui.playlist.components.ItemGrid

@Composable
fun PlaylistScreen(
    isPlaylistScreen: Boolean = false,
    onClickPlaylist: () -> Unit = {},
    onClickHome: () -> Unit = {},
    onClickLibrary: () -> Unit = {},
    viewModel: PlaylistViewModel = viewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val playlistId by remember { mutableLongStateOf(AppCache.playlist?.id!!.toLong()) }
    val playlistName by remember { mutableStateOf(AppCache.playlist?.name!!) }
    LaunchedEffect(Unit) {
        viewModel.processIntent(PlaylistIntent.LoadSongsInPlaylist(context, playlistId))
    }
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
                    titlePlaylist = playlistName,
                    stateSortView = state.isSortView,
                    onToggleSortView = {
                        viewModel.processIntent(PlaylistIntent.ToggleSortView)
                    },
                    stateGridView = state.isGridView,
                    onToggleGridView = {
                        viewModel.processIntent(PlaylistIntent.ToggleGridView)
                    }
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.isGridView) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2)
                        ) {
                            items(state.songs) { song ->
                                val isExpanded = state.expanded && state.selectedSong == song
                                ItemGrid(
                                    song = song,
                                    expanded = isExpanded,
                                    onOpenMenu = {
                                        viewModel.processIntent(PlaylistIntent.OpenMenu(song))
                                    },
                                    onDismissRequest = {
                                        viewModel.processIntent(PlaylistIntent.CloseMenu)
                                    },
                                    onClick = {
                                        viewModel.processIntent(
                                            PlaylistIntent.RemoveSong(
                                                context = context,
                                                songId = song.id,
                                                playlistId = playlistId
                                            )
                                        )
                                    }
                                )
                            }
                        }

                    } else {
                        LazyColumn {
                            items(state.songs) { song ->
                                val isExpanded = state.expanded && state.selectedSong == song
                                ItemColumn(
                                    song = song,
                                    expanded = isExpanded,
                                    onOpenMenu = {
                                        viewModel.processIntent(PlaylistIntent.OpenMenu(song))
                                    },
                                    onDismissRequest = {
                                        viewModel.processIntent(PlaylistIntent.CloseMenu)
                                    },
                                    onClick = {
                                        viewModel.processIntent(
                                            PlaylistIntent.RemoveSong(
                                                context = context,
                                                songId = song.id,
                                                playlistId = playlistId
                                            )
                                        )
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
fun PreviewPlaylist() {
    PlaylistScreen()
}

