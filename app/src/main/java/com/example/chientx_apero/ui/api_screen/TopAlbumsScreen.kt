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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.retrofit.model.AlbumRetrofit
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun TopAlbumsScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {}
) {
    val albums: List<AlbumRetrofit> = AppCache.topAlbums!!
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
                    text = "Top Albums",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.padding(start = 10.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            LazyColumn {
                items(albums) { album ->
                    Row(
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .padding(6.dp)
                            .background(
                                MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp)
                            )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                album.image.last().url.ifEmpty { R.drawable.avatar }
                            ),
                            contentDescription = null,
                            modifier = Modifier.Companion
                                .clip(RoundedCornerShape(10.dp))
                                .size(64.dp)
                        )
                        Box(
                            modifier = Modifier.Companion
                                .padding(start = 12.dp, top = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
                            ) {
                                Text(
                                    text = album.name,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Companion.Bold,
                                    modifier = Modifier.Companion.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = album.artist.name,
                                    fontSize = 14.sp,
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

@Preview(showBackground = true)
@Composable
private fun PrevTopArtistsScreen() {
    TopAlbumsScreen()
}