package com.example.chientx_apero.ui.player_bar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.service.MusicServiceManager
import com.example.chientx_apero.ui.library.components.PlayerBar
import kotlinx.coroutines.delay

@Composable
fun PlayerBarScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerBarViewModel = viewModel(),
    onClickPlayer: () -> Unit = {},
    song: Song? = null
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var currentPosition by remember { mutableIntStateOf(0) }
    val duration = parseDurationToMilliseconds(AppCache.playingSong?.duration ?: "00:00")
    var progress: Float = currentPosition.toFloat() / duration.toFloat()
    MusicServiceManager.bindService(context)

    LaunchedEffect(Unit) {
        while (true) {
            MusicServiceManager.getService()?.let {
                currentPosition = it.getCurrentPosition()
            }
            delay(1000)
        }
    }
    PlayerBar(
        song = AppCache.playingSong!!,
        isPlaySong = state.isPlaySong,
        onClickPlaySong = {
            viewModel.processIntent(
                PlayerBarIntent.HandleSongAction(song ?: AppCache.playingSong!!),
                context
            )
        },
        onClickStopSong = {
            viewModel.processIntent(
                PlayerBarIntent.StopSong,
                context
            )
            AppCache.playingSong = null
        },
        onClickPlayer = {
            onClickPlayer()
        },
        currentTime = progress
    )
}

fun parseDurationToMilliseconds(durationStr: String): Long {
    val parts = durationStr.split(":")
    val minutes = parts[0].toLongOrNull() ?: 0
    val seconds = parts[1].toLongOrNull() ?: 0
    return (minutes * 60 + seconds) * 1000
}