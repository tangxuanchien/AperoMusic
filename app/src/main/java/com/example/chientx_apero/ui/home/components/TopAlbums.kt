package com.example.chientx_apero.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.retrofit.model.AlbumRetrofit

@Composable
fun TopAlbums(
    modifier: Modifier = Modifier.Companion,
    albums: List<AlbumRetrofit>,
    onClickTopAlbums: () -> Unit,
) {
    Box(
        modifier = Modifier.Companion
            .padding(start = 10.dp, end = 14.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.top_albums),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            text = stringResource(R.string.see_all),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    onClickTopAlbums()
                }
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(albums) { album ->
            Row(
                modifier = Modifier.Companion
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        album.image.last().url.ifEmpty {
                            R.drawable.avatar
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(50.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Companion.Crop,
                    alignment = Alignment.Companion.TopCenter
                )
                Column(

                ) {
                    Text(
                        text = album.name,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Companion.Bold,
                        modifier = Modifier.Companion.padding(start = 10.dp)
                    )
                    Text(
                        text = album.artist.name,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.Companion.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}