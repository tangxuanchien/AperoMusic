package com.example.chientx_apero.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.chientx_apero.home_screen.HomeScreen
import com.example.chientx_apero.information_screen.InformationScreen
import com.example.chientx_apero.library_screen.LibraryScreen
import com.example.chientx_apero.login_screen.LoginScreen
import com.example.chientx_apero.signup_screen.SignUpScreen
import com.example.chientx_apero.playlist_screen.PlaylistScreen

@Composable
fun Navigation() {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Login("", "")) }

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
                        backStack.add(Screen.Playlist)
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
            entry<Screen.Login> { screen ->
                LoginScreen(
                    onClickSignUp = {
                        backStack.add(Screen.Signup)
                    },
                    onClickHome = {
                        backStack.clear()
                        backStack.add(Screen.Home)
                    },
                    username = screen.username,
                    password = screen.password
                )
            }
            entry<Screen.Signup> {
                SignUpScreen(
                    onClickLogin = { username, password ->
                        backStack.add(Screen.Login(username, password))
                    }
                )
            }
            entry<Screen.Playlist> {
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
                    isLibraryScreen = true
                )
            }
        }
    )
}