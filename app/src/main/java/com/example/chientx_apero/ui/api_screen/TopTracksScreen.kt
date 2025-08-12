package com.example.chientx_apero.ui.api_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults.itemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.retrofit.model.TrackRetrofit
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.darkTheme
import kotlin.collections.first

@Composable
fun TopTracksScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
) {
    val tracks: List<TrackRetrofit> = AppCache.topTracks!!
    val itemColors = mutableListOf<Color>(
        Color(0xFFFF7777),
        Color(0xFFFFFA77),
        Color(0xFF4462FF),
        Color(0xFF14FF00),
        Color(0xFFE231FF),
        Color(0xFF00FFFF),
        Color(0xFFFB003C),
        Color(0xFFF2A5FF)
    )
    MaterialTheme(
        colorScheme = darkTheme.color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 18.dp, horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(24.dp)
                        .clickable {
                            onClickBack()
                        },
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Top Tracks",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                itemsIndexed(tracks) { index, track ->
                    val color = itemColors[index % itemColors.size]
                    Box(
                        modifier = Modifier.Companion
                            .padding(vertical = 12.dp, horizontal = 10.dp)
                            .size(150.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(track.image.last().url),
                            contentDescription = null,
                            modifier = Modifier.Companion
                                .size(150.dp),
                            contentScale = ContentScale.Companion.Crop,
                            alignment = Alignment.Companion.TopCenter,
                            alpha = 0.6f
                        )
                        Text(
                            text = track.name,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Companion.Bold,
                            modifier = Modifier.Companion
                                .width(150.dp)
                                .padding(start = 8.dp, top = 14.dp)
                                .align(Alignment.Companion.TopStart)
                        )
                        Column(
                            modifier = Modifier.Companion
                                .align(Alignment.Companion.BottomStart)
                                .padding(bottom = 14.dp)
                        ) {
                            Row(
                                modifier = Modifier.Companion
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.Companion.vectorResource(R.drawable.listen),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.Companion
                                        .size(20.dp)
                                )
                                Text(
                                    text = track.listeners,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.Companion
                                        .padding(start = 6.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.Companion
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.Companion.vectorResource(R.drawable.artist),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.Companion
                                        .size(20.dp)
                                )
                                Text(
                                    text = track.artist.name,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.Companion
                                        .padding(start = 6.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier.Companion
                                .background(color)
                                .height(8.dp)
                                .width(150.dp)
                                .align(Alignment.Companion.BottomStart)
                                .zIndex(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevTopTracksScreen() {
    TopTracksScreen()
}