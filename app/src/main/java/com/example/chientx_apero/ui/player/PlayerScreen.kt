package com.example.chientx_apero.ui.player

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.util.Logger
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.service.MusicServiceManager
import com.example.chientx_apero.ui.player.components.HeaderPlayer
import com.example.chientx_apero.ui.player.components.PlayerBar
import com.example.chientx_apero.ui.player.components.SongInfo
import com.example.chientx_apero.ui.theme.darkTheme
import kotlinx.coroutines.delay


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
    var isServiceBound by remember { mutableStateOf(false) }
    var isRandomSong by remember { mutableStateOf(false) }
    var isReplaySong by remember { mutableStateOf(false) }
    var isNextSong by remember { mutableStateOf(AppCache.playlist != null) }
    var isPreviousSong by remember { mutableStateOf(AppCache.playlist != null) }

    LaunchedEffect(state.isPlaySong) {
        if (state.isPlaySong) {
            while (MusicServiceManager.getService() == null) {
                delay(100)
            }
            isServiceBound = true
        } else {
            isServiceBound = false
        }
    }

    LaunchedEffect(isServiceBound) {
        if (isServiceBound) {
            while (true) {
                MusicServiceManager.getService()?.let {
                    sliderPosition = it.getCurrentPosition().toFloat()
                }
                delay(1000)
            }
        }
    }


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
                        AppCache.isPlayingSong = state.isPlaySong
                        onClickBack()
                    },
                    onClickBackSelected = {
                        AppCache.isPlayingSong = state.isPlaySong
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
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.Companion
                                .padding(vertical = 6.dp, horizontal = 4.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = formatTime(sliderPosition.toInt()),
                                fontWeight = FontWeight.Companion.Bold,
                                fontSize = 14.sp,
                                color = Color(0x99CCCCCC)
                            )
                            Text(
                                text = state.song!!.duration,
                                fontWeight = FontWeight.Companion.Bold,
                                fontSize = 14.sp,
                                color = Color(0x99CCCCCC)
                            )
                        }
                        PlayerBar(
                            onClickTogglePlayback = {
                                viewModel.processIntent(PlayerIntent.TogglePlayback, context)
                            },
                            onClickReplay = {
                                if (AppCache.playlist != null) {
                                    isReplaySong = !isReplaySong
                                    if (isReplaySong == true) {
                                        viewModel.processIntent(PlayerIntent.ReplaySong, context)
                                    }
                                }
                            },
                            onClickRandomSong = {
                                if (AppCache.playlist != null) {
                                    isRandomSong = !isRandomSong
                                    if (isRandomSong == true) {
                                        viewModel.processIntent(PlayerIntent.RandomSong, context)
                                    }
                                }
                            },
                            onClickPreviousSong = {
                                if (isPreviousSong == true) {
                                    viewModel.processIntent(PlayerIntent.PreviousSong, context)
                                }
                            },
                            onClickNextSong = {
                                if (isNextSong == true) {
                                    viewModel.processIntent(PlayerIntent.NextSong, context)
                                }
                            },
                            isPlaySong = state.isPlaySong,
                            isRandomSong = isRandomSong,
                            isReplaySong = isReplaySong,
                            isPreviousSong = isPreviousSong,
                            isNextSong = isNextSong,
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