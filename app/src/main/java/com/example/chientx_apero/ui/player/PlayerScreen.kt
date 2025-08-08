package com.example.chientx_apero.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.player.components.HeaderPlayer
import com.example.chientx_apero.ui.player.components.PlayerBar
import com.example.chientx_apero.ui.player.components.PlayerSlider
import com.example.chientx_apero.ui.player.components.SongInfo
import com.example.chientx_apero.ui.theme.darkTheme


@Composable
fun PlayerScreen(
    onClickBack: () -> Unit,
    onClickHome: () -> Unit,
    viewModel: PlayerViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var currentTheme by remember { mutableStateOf(darkTheme) }

    MaterialTheme(
        colorScheme = currentTheme.color
    ) {
        Box(
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 18.dp, horizontal = 10.dp)
                    .fillMaxSize()
            ) {
                HeaderPlayer(
                    onClickBackUnSelected = {
                        onClickBack()
                    },
                    onClickBackSelected = {
                        onClickHome()
                    },
                )
                Box(
                    Modifier.width(350.dp)
                ) {
                    Column(
                        modifier = Modifier.Companion.fillMaxWidth()
                    ) {
                        SongInfo(
                            image = R.drawable.justin_2,
                            name = "Lovely",
                            artist = "Phuong Ly"
                        )
                        PlayerSlider()
                        PlayerBar(
                            onClickPlaySong = {
                                viewModel.processIntent(PlayerIntent.PlaySong(context), context)
                            },
                            onClickPauseSong = {
                                viewModel.processIntent(PlayerIntent.PauseSong(context), context)
                            },
                            onClickReplay = {},
                            onClickRandomSong = {},
                            onClickPreviousSong = {},
                            onClickNextSong = {},
                            isPlaySong = state.isPlaySong
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevPlayerScreen() {
    PlayerScreen(
        onClickBack = {},
        onClickHome = {}
    )
}