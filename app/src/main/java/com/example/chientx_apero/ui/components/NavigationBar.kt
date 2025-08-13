package com.example.chientx_apero.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chientx_apero.R

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    onClickPlaylist: () -> Unit,
    onClickLibrary: () -> Unit,
    onClickHome: () -> Unit,
    isHomeScreen: Boolean = false,
    isLibraryScreen: Boolean = false,
    isPlaylistScreen: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationIcon(
            text = stringResource(R.string.home),
            icon = R.drawable.home,
            onClick = onClickHome,
            isScreen = isHomeScreen
        )
        NavigationIcon(
            text = stringResource(R.string.library),
            icon = R.drawable.library,
            onClick = onClickLibrary,
            isScreen = isLibraryScreen
        )
        NavigationIcon(
            text = stringResource(R.string.playlist),
            icon = R.drawable.playlist,
            onClick = onClickPlaylist,
            isScreen = isPlaylistScreen
        )
    }
}