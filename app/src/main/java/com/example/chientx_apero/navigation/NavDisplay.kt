package com.example.chientx_apero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.model.PreferenceManager
import com.example.chientx_apero.room_db.repository.UserRepository
import com.example.chientx_apero.ui.home.HomeScreen
import com.example.chientx_apero.ui.information.InformationScreen
import com.example.chientx_apero.ui.library.LibraryScreen
import com.example.chientx_apero.ui.login.LoginScreen
import com.example.chientx_apero.ui.my_playlist.MyPlaylistScreen
import com.example.chientx_apero.ui.signup.SignUpScreen
import com.example.chientx_apero.ui.playlist.PlaylistScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun Navigation() {
    val context = LocalContext.current
    var startScreen: Screen = Screen.Login("", "")
    LaunchedEffect(Unit) {
        if (PreferenceManager.isLoggedIn()) {
            val repository = UserRepository(context)
            val userId = PreferenceManager.getSaveUserId()
            if (userId != null) {
                val user = repository.checkExistAccount(userId)
                AppCache.currentUser = user
                startScreen = Screen.Home
            }
        }
    }
    val backStack = remember { mutableStateListOf<Screen>(startScreen) }

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
                        backStack.clear()
                        backStack.add(Screen.MyPlaylist)
                    },
                    onClickLibrary = {
                        backStack.clear()
                        backStack.add(Screen.Library)
                    },
                    onClickBack = {
                        backStack.removeLastOrNull()
                    },
                    isHomeScreen = true
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
                    isPlaylistScreen = true
                )
            }
            entry<Screen.Information> {
                InformationScreen(
                    onClickBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Screen.Library> {
                LibraryScreen(
                    onClickBack = { backStack.removeLastOrNull() },
                    onClickPlaylist = {
                        backStack.add(Screen.MyPlaylist)
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
                    }
                )
            }
        }
    )
}