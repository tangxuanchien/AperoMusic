package com.example.chientx_apero.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.chientx_apero.model.Playlist

sealed interface Screen {
    data object Signup : Screen
    data class Login(val username: String, val password: String) : Screen
    data object Playlists : Screen
    data object Home : Screen
    data object Information : Screen
    data object Library : Screen
    data object MyPlaylist : Screen
}