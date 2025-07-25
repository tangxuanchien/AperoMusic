package com.example.chientx_apero.music_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.chientx_apero.R


class MusicScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen(songs)
        }
    }
}

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
fun SongItemColumn(
    song: Song,
    onOpenMenu: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(song.image),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(64.dp)
        )
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = song.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = song.artist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0x99CCCCCC)
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = song.duration,
                    fontSize = 18.sp,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.option),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onOpenMenu()
                        }
                )
            }
            DropdownItemApp(
                expanded = expanded,
                onClick = onClick,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun SongItemGrid(
    song: Song,
    onOpenMenu: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        Color(0x88000000),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(34.dp)
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.option),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onOpenMenu()
                        }
                )
            }
            Image(
                painter = painterResource(song.image),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(160.dp)
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = song.name,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 2.dp)
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = song.artist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0x99CCCCCC)
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = song.duration,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }
            }
            DropdownItemApp(
                expanded = expanded,
                onClick = onClick,
                onDismissRequest = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 30.dp)
            )
        }
    }
}

@Composable
fun DropdownItemApp(
    expanded: Boolean,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.Black.copy(0.8f), shape = RoundedCornerShape(15.dp))
    ) {
        DropdownMenu(
            modifier = Modifier
                .background(Color.Black.copy(0.8f))
                .padding(horizontal = 10.dp),
            expanded = expanded,
            onDismissRequest = { onDismissRequest() }
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.remove),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "Remove from playlist",
                        color = Color.White
                    )
                },
                onClick = {
                    onClick()
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 50.dp)
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.share),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "Share (comming soon)",
                        color = Color.White.copy(0.5f)
                    )
                },
                onClick = {}
            )
        }
    }
}

@Composable
fun HomeScreen(songs: List<Song>) {
//    default gridview true sortview false
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

