package com.example.chientx_apero.ui.api_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.retrofit.model.ArtistRetrofit
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.darkTheme
import kotlin.collections.first

@Composable
fun TopArtistsScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {}
) {
    val artists: List<ArtistRetrofit> = AppCache.topArtists!!
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
                    text = "Top Artist",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
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
                            alpha = 0.8f
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
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevTopAlbumsScreen() {
    TopArtistsScreen()
}