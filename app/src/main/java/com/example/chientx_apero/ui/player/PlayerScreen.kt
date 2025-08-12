package com.example.chientx_apero.ui.player

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    MaterialTheme(
        colorScheme = currentTheme.color
    ) {
        LaunchedEffect(state.currentTime) {
            sliderPosition = state.currentTime.toFloat()
        }
//        LaunchedEffect(sliderPosition, state.duration) {
//            if (sliderPosition >= state.duration.toFloat()) {
//                viewModel.processIntent(PlayerIntent.NextSong, context)
//                Log.d("Slider", "PlayerScreen: Next Song (auto end)")
//            }
//        }
        DisposableEffect(Unit) {
            viewModel.bindService(context)
            onDispose {
                viewModel.unbindService(context)
            }
        }
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
                            image = state.song?.image,
                            name = state.song?.name!!,
                            artist = state.song?.artist!!
                        )
                        Slider(
                            value = sliderPosition,
                            onValueChange = {
                                sliderPosition = it
                                viewModel.processIntent(PlayerIntent.SeekToProgress(it), context)
                            },
                            valueRange = 0f..state.duration.toFloat(),
                            colors = SliderDefaults.colors(
                                activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                inactiveTrackColor = Color.Companion.DarkGray
                            ),
                            modifier = Modifier.Companion
                                .fillMaxWidth()
                                .height(6.dp)
                        )
                        PlayerSlider(
                            song = state.song,
                            onValueChangeFinished = {

                            },
                            onValueChange = {

                            },
                            value = sliderPosition,
                            currentTime = formatTime(sliderPosition.toInt())
                        )
                        PlayerBar(
                            onClickTogglePlayback = {
                                viewModel.processIntent(PlayerIntent.TogglePlayback, context)
                            },
                            onClickReplay = {
                                viewModel.processIntent(PlayerIntent.ReplaySong, context)
                            },
                            onClickRandomSong = {
                                viewModel.processIntent(PlayerIntent.RandomSong, context)
                            },
                            onClickPreviousSong = {
                                viewModel.processIntent(PlayerIntent.PreviousSong, context)
                            },
                            onClickNextSong = {
                                viewModel.processIntent(PlayerIntent.NextSong, context)
                            },
                            isPlaySong = state.isPlaySong
                        )
                    }
                }
            }
        }
    }
}

fun formatTime(time: Int): String {
    val totalSeconds = time / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun PrevPlayerScreen() {
    PlayerScreen(
        onClickBack = {},
        onClickHome = {}
    )
}