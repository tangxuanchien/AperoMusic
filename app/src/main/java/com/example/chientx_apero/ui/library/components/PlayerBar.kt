package com.example.chientx_apero.ui.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun PlayerBar(
    modifier: Modifier = Modifier,
    song: Song,
    onClickPlaySong: () -> Unit,
    onClickPlayer: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(60.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable{
                onClickPlayer()
            }
    ) {
        Column(
            modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
        ) {
            Row(
                verticalAlignment = Alignment.Companion.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.play_fill),
                    contentDescription = "Next",
                    modifier = Modifier.Companion
                        .size(24.dp)
                        .clickable {
                            onClickPlaySong()
                        },
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = song.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = song.duration,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )
            }
        }
    }
}