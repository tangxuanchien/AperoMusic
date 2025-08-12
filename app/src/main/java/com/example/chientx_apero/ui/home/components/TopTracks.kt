package com.example.chientx_apero.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.retrofit.model.TrackRetrofit
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun TopTracks(
    modifier: Modifier = Modifier.Companion,
    tracks: List<TrackRetrofit>,
    itemColors: List<Color>,
    onClickTopTracks: () -> Unit,
) {
    Box(
        modifier = Modifier.Companion
            .padding(start = 10.dp, end = 14.dp, top = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Top tracks",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
        )
        Text(
            text = "See all",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Companion.Underline,
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterEnd)
                .clickable {
                    onClickTopTracks()
                }
        )
    }
    LazyRow(
        modifier = Modifier.Companion
            .fillMaxWidth()
    ) {
        itemsIndexed(tracks) { index, track ->
            val color = itemColors[index % itemColors.size]
            Box(
                modifier = Modifier.Companion
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .size(150.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(track.image.first().url),
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
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .padding(start = 10.dp, top = 14.dp)
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
                        .fillMaxWidth()
                        .align(Alignment.Companion.BottomStart)
                        .zIndex(1f)
                )
            }
        }
    }
}