package com.example.chientx_apero.ui.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun PlayerBar(
    onClickTogglePlayback: () -> Unit,
    onClickReplay: () -> Unit,
    onClickPreviousSong: () -> Unit,
    onClickNextSong: () -> Unit,
    onClickRandomSong: () -> Unit,
    isPlaySong: Boolean = false
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.random),
            contentDescription = "Back Home",
            modifier = Modifier.Companion
                .size(20.dp)
                .clickable {
                    onClickRandomSong()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.seek_to_back),
            contentDescription = "Back Home",
            modifier = Modifier.Companion
                .size(24.dp)
                .clickable {
                    onClickPreviousSong()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
        Box(
            modifier = Modifier.Companion
                .background(
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    RoundedCornerShape(50.dp)
                )
                .padding(18.dp)
                .clickable {
                    onClickTogglePlayback()
                }
        ) {
            Icon(
                imageVector = ImageVector.Companion.vectorResource(
                    if (isPlaySong) {
                        R.drawable.pause
                    } else {
                        R.drawable.play
                    }
                ),
                contentDescription = "Play",
                modifier = Modifier.Companion
                    .size(30.dp)
                    .align(Alignment.Companion.Center),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.seek_to_next),
            contentDescription = "Next",
            modifier = Modifier.Companion
                .size(24.dp)
                .clickable {
                    onClickNextSong()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.replay),
            contentDescription = "Replay",
            modifier = Modifier.Companion
                .size(20.dp)
                .clickable {
                    onClickReplay()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevPlayerBar() {
    MaterialTheme(
        colorScheme = darkTheme.color
    ) {
        PlayerBar(
            onClickTogglePlayback = {},
            onClickReplay = {},
            onClickRandomSong = {},
            onClickPreviousSong = {},
            onClickNextSong = {}
        )
    }
}