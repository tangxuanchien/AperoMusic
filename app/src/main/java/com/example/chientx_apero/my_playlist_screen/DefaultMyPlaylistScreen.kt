package com.example.chientx_apero.my_playlist_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun DefaultMyPlaylistScreen(
    onClickAddMyPlaylist: () -> Unit
) {
    Text(
        text = "You don't have any playlists. Click the \"+\" button to add",
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Companion.Center,
        modifier = Modifier.Companion
            .padding(
                bottom = 25.dp,
                start = 45.dp,
                end = 45.dp,
                top = 200.dp
            )
    )
    Icon(
        imageVector = ImageVector.Companion.vectorResource(R.drawable.plus),
        contentDescription = "Add playlist",
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.Companion.clickable {
            onClickAddMyPlaylist()
        }
    )
}