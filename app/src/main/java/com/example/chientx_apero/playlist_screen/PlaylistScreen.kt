package com.example.chientx_apero.playlist_screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chientx_apero.R
import com.example.chientx_apero.home_screen.NavigationBar
import com.example.chientx_apero.ui.theme.darkTheme

data class Song(val name: String, val artist: String, val duration: String, val image: Int)

@Composable
fun PlaylistScreen(
    isPlaylistScreen: Boolean = false
) {
    val songs = mutableListOf<Song>(
        Song("Let Go", "Central Cee", "04:30", R.drawable.central_cee1),
        Song("Did It First", "Central Cee", "04:30", R.drawable.central_cee2),
        Song("What Do You Mean?", "Justin Bieber", "00:30", R.drawable.justin_1),
        Song("Baby", "Justin Bieber", "03:33", R.drawable.justin_2),
        Song("BAND4BAND", "Central Cee", "03:25", R.drawable.central_cee3),
        Song("Doja", "Central Cee", "05:00", R.drawable.central_cee4),
        Song("Deja Vu", "Justin Bieber", "02:56", R.drawable.justin_3),
        Song("Sprinter", "Central Cee", "04:00", R.drawable.central_cee5),
        Song("Love Yourself", "Justin Bieber", "03:11", R.drawable.justin_4),
        Song("Yummy", "Justin Bieber", "04:30", R.drawable.justin_5)
    )
    var stateGridView by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }
    val songList = remember { mutableStateListOf<Song>().apply { addAll(songs) } }
    var stateSortView by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var currentTheme by remember { mutableStateOf(darkTheme) }

    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography
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
                    stateSortView = stateSortView,
                    onToggleSortView = { stateSortView = !stateSortView },
                    stateGridView = stateGridView,
                    onToggleGridView = { stateGridView = !stateGridView }
                )

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (stateGridView) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2)
                        ) {
                            items(songList) { song ->
                                var isExpanded = expanded && selectedSong == song
                                ItemGrid(
                                    song = song,
                                    expanded = isExpanded,
                                    onOpenMenu = {
                                        if (selectedSong == song && expanded) {
                                            expanded = false
                                            selectedSong = null
                                        } else {
                                            expanded = true
                                            selectedSong = song
                                        }
                                    },
                                    onDismissRequest = {
                                        expanded = false
                                        selectedSong = null
                                    },
                                    onClick = {
                                        songList.remove(song)
                                        selectedSong = null
                                        expanded = false
                                    }
                                )
                            }
                        }

                    } else {
                        LazyColumn {
                            items(songList) { song ->
                                var isExpanded = expanded && selectedSong == song
                                ItemColumn(
                                    song = song,
                                    expanded = isExpanded,
                                    onOpenMenu = {
                                        if (selectedSong == song && expanded) {
                                            expanded = false
                                            selectedSong = null
                                        } else {
                                            expanded = true
                                            selectedSong = song
                                        }
                                    },
                                    onDismissRequest = {
                                        expanded = false
                                        selectedSong = null
                                    },
                                    onClick = {
                                        songList.remove(song)
                                        selectedSong = null
                                        expanded = false
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
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewItemGrid() {
    PlaylistScreen()
}

