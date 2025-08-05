package com.example.chientx_apero.ui.my_playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.PlaylistModel
import com.example.chientx_apero.room_db.entity.Playlist

@Composable
fun ItemMyPlaylist(
    playlist: Playlist,
    onOpenMenu: () -> Unit,
    onClickRemovePlaylist: () -> Unit,
    onClickRename: () -> Unit,
    onClickPlaylist: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
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
                            onClickPlaylist()
                        }
                )
                Text(
                    text = "${playlist.totalSongs} songs",
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Row(
                modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd)
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.option),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.Companion
                        .size(20.dp)
                        .clickable {
                            onOpenMenu()
                        }
                )
            }
            DropdownMenuMyPlaylist(
                expanded = expanded,
                onClickRename = onClickRename,
                onClickRemovePlaylist = onClickRemovePlaylist,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd)
            )
        }
    }
}