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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.room.util.copy
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun PlayerBar(
    modifier: Modifier = Modifier,
    song: Song,
    onClickPlaySong: () -> Unit,
    onClickPlayer: () -> Unit,
    isPlaySong: Boolean,
    currentTime: () -> Float
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.cancel),
            contentDescription = "Cancel",
            modifier = Modifier.Companion
                .size(24.dp)
                .padding(vertical = 5.dp)
                .clickable {
                    onClickPlaySong()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
        LinearProgressIndicator(
            progress = {
                currentTime()
            },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Box(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
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
                        imageVector = ImageVector.Companion.vectorResource(
                            if (isPlaySong) {
                                R.drawable.pause
                            } else {
                                R.drawable.play
                            }
                        ),
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
}

@Preview
@Composable
private fun PrevPlayerBar() {
    MaterialTheme(
        colorScheme = darkTheme.color
    ) {
        PlayerBar(
            song = Song(1L, "Lover", "".toUri(), null, "Justin", "03:36", "remote"),
            onClickPlaySong = {},
            onClickPlayer = {},
            isPlaySong = false,
            currentTime = {0f}
        )
    }
}