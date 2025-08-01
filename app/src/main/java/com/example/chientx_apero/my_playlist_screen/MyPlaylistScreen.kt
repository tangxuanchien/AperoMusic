package com.example.chientx_apero.my_playlist_screen

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import com.example.chientx_apero.playlist_screen.HeaderPlaylist
import com.example.chientx_apero.playlist_screen.ItemColumn
import com.example.chientx_apero.playlist_screen.ItemGrid
import com.example.chientx_apero.playlist_screen.PlaylistIntent
import com.example.chientx_apero.playlist_screen.PlaylistViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.home_screen.NavigationBar
import com.example.chientx_apero.playlist_screen.PlaylistScreen

@Composable
fun MyPlaylistScreen(
    isPlaylistScreen: Boolean = false,
    viewModel: MyPlaylistViewModel = viewModel(),
) {
    val context = LocalContext.current
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
                HeaderPlaylist()
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
                                    onClick = {
                                        viewModel.processIntent(
                                            MyPlaylistIntent.RemovePlaylist(
                                                playlist
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (!state.showPopup) {
                                Text(
                                    text = "You don't have any playlists. Click the \"+\" button to add",
                                    fontSize = 25.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Companion.Center,
                                    modifier = Modifier
                                        .padding(
                                            bottom = 25.dp,
                                            start = 45.dp,
                                            end = 45.dp,
                                            top = 200.dp
                                        )
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.plus),
                                    contentDescription = "Add playlist",
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.clickable {
                                        viewModel.processIntent(MyPlaylistIntent.AddMyPlaylist)
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
                    onClickLibrary = {},
                    isPlaylistScreen = isPlaylistScreen
                )
                if (state.showPopup) {
                    PopupAddMyPlaylist(
                        onDismissRequest = {

                        },
                        onCreateMyPlaylist = {

                        },
                        onValueChange = {
                            viewModel.processIntent(MyPlaylistIntent.TitleMyPlaylistChanged(it))
                        },
                        value = state.titleMyPlaylist
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewItemGrid() {
    MyPlaylistScreen(
    )
}

