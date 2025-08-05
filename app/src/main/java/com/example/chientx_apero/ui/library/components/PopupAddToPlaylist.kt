package com.example.chientx_apero.ui.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.PlaylistModel
import com.example.chientx_apero.room_db.entity.Playlist
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun PopupAddToPlaylist(
    onDismissRequest: () -> Unit = {},
    onClickCreatePlaylist: () -> Unit = {},
    onClickAddToPlaylist: (id: Int) -> Unit = {},
    currentTheme: ThemeData = darkTheme,
    playlists: List<Playlist>
    = mutableStateListOf<Playlist>()
) {
    MaterialTheme(
        colorScheme = currentTheme.color
    ) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Box(
                modifier = Modifier.Companion
                    .height(460.dp)
                    .width(350.dp)
                    .background(
                        MaterialTheme.colorScheme.inverseOnSurface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp,
                        Color.Companion.LightGray,
                        androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.Companion.CenterHorizontally,
                    modifier = Modifier.Companion
                        .padding(top = 20.dp, end = 10.dp, start = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Choose playlist",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Companion.Center,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                    )
                    if (playlists.isEmpty()) {
                        Text(
                            text = "You don't have any playlists. Click the \"+\" button to add",
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Companion.Center,
                            modifier = Modifier
                                .padding(bottom = 25.dp, start = 45.dp, end = 45.dp, top = 100.dp)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.plus),
                            contentDescription = "Add playlist",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable {
                                onClickCreatePlaylist()
                            }
                        )
                    } else {
                        LazyColumn {
                            items(playlists) { playlist ->
                                Row(
                                    modifier = Modifier.Companion
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                                        .padding(vertical = 8.dp, horizontal = 18.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(playlist.image ?: R.drawable.justin_2),
                                        contentDescription = null,
                                        modifier = Modifier.Companion
                                            .clip(RoundedCornerShape(10.dp))
                                            .size(64.dp)
                                    )
                                    Box(
                                        modifier = Modifier.Companion
                                            .padding(start = 12.dp, top = 8.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
                                        ) {
                                            Text(
                                                text = playlist.name,
                                                color = MaterialTheme.colorScheme.onBackground,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Companion.Bold,
                                                modifier = Modifier.Companion
                                                    .padding(bottom = 4.dp)
                                                    .clickable{
                                                        onClickAddToPlaylist(playlist.id)
                                                    }
                                            )
                                            Text(
                                                text = "${playlist.totalSongs} songs",
                                                fontWeight = FontWeight.Companion.Bold,
                                                fontSize = 16.sp,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPopUp() {
    PopupAddToPlaylist()
}