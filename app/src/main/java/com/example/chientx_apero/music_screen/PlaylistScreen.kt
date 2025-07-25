package com.example.chientx_apero.music_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

data class Song(val name: String, val artist: String, val duration: String, val image: Int)

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

@Composable
fun HomeScreen(songs: List<Song>) {
    var stateGridView by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }
    val songList = remember { mutableStateListOf<Song>().apply { addAll(songs) } }
    var stateSortView by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(18.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            if (stateSortView) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.cancel),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterStart)
                        .clickable {
                            stateSortView = false
                        }
                )
            }
            Text(
                text = if (!stateSortView) {
                    "My playlist"
                } else {
                    "Sorting"
                },
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            ) {
                if (stateGridView) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.column),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { stateGridView = false }
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.grid),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { stateGridView = true }
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                if (stateSortView) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.done),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                stateSortView = false
                            }
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.sort),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .clickable {
                                stateSortView = true
                            }
                    )
                }
            }
        }
        if (stateGridView) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(songList) { song ->
                    var isExpanded = expanded && selectedSong == song
                    SongItemGrid(
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
                    SongItemColumn(
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
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewItemGrid() {
//    SongItemGrid(Song("Let Go", "Central Cee", "04:30", R.drawable.central_cee1))
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMusic() {
//    HomeScreen(songs = songs)
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewItemColumn() {
//    SongItemColumn(Song("Let Go", "Central Cee", "04:30", R.drawable.central_cee1), onOpenMenu = {})
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewOptionList() {
//    DropdownItem()
//}

