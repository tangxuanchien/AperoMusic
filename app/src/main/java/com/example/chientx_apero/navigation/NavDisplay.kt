package com.example.chientx_apero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.model.PreferenceManager
import com.example.chientx_apero.room_db.repository.UserRepository
import com.example.chientx_apero.ui.api_screen.TopAlbumsScreen
import com.example.chientx_apero.ui.api_screen.TopArtistsScreen
import com.example.chientx_apero.ui.api_screen.TopTracksScreen
import com.example.chientx_apero.ui.home.HomeScreen
import com.example.chientx_apero.ui.information.InformationScreen
import com.example.chientx_apero.ui.library.LibraryScreen
import com.example.chientx_apero.ui.login.LoginScreen
import com.example.chientx_apero.ui.my_playlist.MyPlaylistScreen
import com.example.chientx_apero.ui.player.PlayerScreen
import com.example.chientx_apero.ui.player_bar.PlayerBarScreen
import com.example.chientx_apero.ui.playlist.PlaylistScreen
import com.example.chientx_apero.ui.settings.SettingsScreen
import com.example.chientx_apero.ui.signup.SignUpScreen

@Composable
fun Navigation() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (PreferenceManager.isLoggedIn()) {
            val repository = UserRepository(context)
            val userId = PreferenceManager.getSaveUserId()
            if (userId != null) {
                val user = repository.checkExistAccount(userId)
                AppCache.currentUser = user
            }
        }
    }
    val backStack = remember {
        mutableStateListOf<Screen>(
            if (PreferenceManager.isLoggedIn()) {
                Screen.Home
            } else {
                Screen.Login("", "")
            }
        )
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.Home> { key ->
                HomeScreen(
                    onClickProfile = {
                        backStack.add(Screen.Information)
                    },
                    onClickPlaylist = {
                        backStack.add(Screen.MyPlaylist)
                    },
                    onClickLibrary = {
                        backStack.add(Screen.Library)
                    },
                    onClickPlayer = {
                        backStack.add(Screen.Player)
                    },
                    onClickTopAlbums = {
                        backStack.add(Screen.TopAlbums)
                    },
                    onClickTopArtists = {
                        backStack.add(Screen.TopArtists)
                    },
                    onClickTopTracks = {
                        backStack.add(Screen.TopTracks)
                    },
                    onClickSettings = {
                        backStack.add(Screen.Settings)
                    },
                    isHomeScreen = true
                )
            }
            entry<Screen.TopArtists> {
                TopArtistsScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.TopAlbums> {
                TopAlbumsScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.TopTracks> {
                TopTracksScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Settings> {
                SettingsScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Login> {
                LoginScreen(
                    onClickSignUp = {
                        backStack.add(Screen.Signup)
                    },
                    onClickHome = {
                        backStack.clear()
                        backStack.add(Screen.Home)
                    },
                    username = it.username,
                    password = it.password
                )
            }
            entry<Screen.Signup> {
                SignUpScreen(
                    onClickLogin = { username, password ->
                        backStack.add(Screen.Login(username, password))
                    }
                )
            }
            entry<Screen.Playlists> {
                PlaylistScreen(
                    isPlaylistScreen = true,
                    onClickMyPlaylist = {
                        backStack.add(Screen.MyPlaylist)
                    },
                    onClickLibrary = {
                        backStack.add(Screen.Library)
                    },
                    onClickHome = {
                        backStack.add(Screen.Home)
                    },
                    onClickPlayer = {
                        backStack.add(Screen.Player)
                    }
                )
            }
            entry<Screen.Information> {
                InformationScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Player> {
                PlayerScreen(
                    onClickHome = {
                        backStack.add(Screen.Home)
                    },
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Library> {
                LibraryScreen(
                    onClickBack = { backStack.removeLastOrNull() },
                    onClickPlaylist = {
                        backStack.add(Screen.MyPlaylist)
                    },
                    onClickPlayer = {
                        backStack.add(Screen.Player)
                    },
                    onClickHome = {
                        backStack.add(Screen.Home)
                    },
                    onClickLibrary = {
                        backStack.add(Screen.Library)
                    },
                    isLibraryScreen = true
                )
            }
            entry<Screen.MyPlaylist> {
                MyPlaylistScreen(
                    isPlaylistScreen = true,
                    onClickLibrary = {
                        backStack.clear()
                        backStack.add(Screen.Library)
                    },
                    onClickPlaylist = {
                        backStack.clear()
                        backStack.add(Screen.Playlists)
                    },
                    onClickHome = {
                        backStack.clear()
                        backStack.add(Screen.Home)
                    }
                )
            }
        }
    )
}