package com.example.chientx_apero.navigation

sealed interface Screen {
    data object Signup : Screen
    data class Login(val username: String, val password: String) : Screen
    data object Playlists : Screen
    data object Home : Screen
    data object Information : Screen
    data object Library : Screen
    data class MyPlaylist(val showPopup: Boolean = false) : Screen
    data object Player : Screen
    data object TopAlbums : Screen
    data object TopArtists : Screen
    data object TopTracks : Screen
    data object Settings : Screen
}