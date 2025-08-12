package com.example.chientx_apero.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun TopArtists(
    modifier: Modifier = Modifier.Companion,
    artists: List<ArtistRetrofit>,
    onClickTopArtists: () -> Unit,
) {
    Box(
        modifier = Modifier.Companion
            .padding(start = 10.dp, end = 14.dp, top = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Top artists",
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
                    onClickTopArtists()
                }
        )
    }
    LazyRow(
        modifier = Modifier.Companion
            .fillMaxWidth()
    ) {
        items(artists) { artist ->
            Box(
                modifier = Modifier.Companion
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .size(180.dp)
                    .clip(RoundedCornerShape(6.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(artist.image.last().url),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(180.dp),
                    contentScale = ContentScale.Companion.Crop,
                    alignment = Alignment.Companion.TopCenter,
                    alpha = 0.6f
                )
                Text(
                    text = artist.name,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .padding(start = 10.dp, top = 14.dp)
                        .align(Alignment.Companion.TopStart)
                )
            }
        }
    }
}