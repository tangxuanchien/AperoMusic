package com.example.chientx_apero.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.home.components.HeaderHome
import com.example.chientx_apero.ui.home.components.Ranking
import com.example.chientx_apero.ui.home.components.TopAlbums
import com.example.chientx_apero.ui.home.components.TopArtists
import com.example.chientx_apero.ui.home.components.TopTracks
import com.example.chientx_apero.ui.player_bar.PlayerBarScreen
import com.example.chientx_apero.ui.player_bar.PlayerBarViewModel
import com.example.chientx_apero.ui.theme.darkTheme


@Composable
fun HomeScreen(
    onClickProfile: () -> Unit = {},
    onClickPlaylist: () -> Unit = {},
    onClickLibrary: () -> Unit = {},
    onClickHome: () -> Unit = {},
    isHomeScreen: Boolean = false,
    onClickBack: () -> Unit = {},
    onClickTopAlbums: () -> Unit = {},
    onClickSettings: () -> Unit = {},
    onClickTopArtists: () -> Unit = {},
    onClickTopTracks: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
    playerBarViewModel: PlayerBarViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val itemColors = mutableListOf<Color>(
        Color(0xFFFF7777),
        Color(0xFFFFFA77),
        Color(0xFF4462FF),
        Color(0xFF14FF00),
        Color(0xFFE231FF),
        Color(0xFF00FFFF),
        Color(0xFFFB003C),
        Color(0xFFF2A5FF)
    )

    LaunchedEffect(Unit) {
        viewModel.processIntent(HomeIntent.LoadData, context)
    }
    MaterialTheme(
        colorScheme = darkTheme.color
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 18.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                HeaderHome(
                    onClickProfile = onClickProfile,
                    onClickSettings = onClickSettings,
                    user = AppCache.currentUser
                )
                Ranking()
                TopAlbums(
                    albums = state.topAlbums?.take(6) ?: emptyList(),
                    onClickTopAlbums = {
                        AppCache.topAlbums = state.topAlbums
                        onClickTopAlbums()
                    }
                )
                TopTracks(
                    tracks = state.topTracks?.take(5) ?: emptyList(),
                    itemColors = itemColors,
                    onClickTopTracks = {
                        AppCache.topTracks = state.topTracks
                        onClickTopTracks()
                    }
                )
                TopArtists(
                    artists = state.topArtists?.take(5) ?: emptyList(),
                    onClickTopArtists = {
                        AppCache.topArtists = state.topArtists
                        onClickTopArtists()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                if (AppCache.playingSong != null) {
                    PlayerBarScreen(
                        viewModel = playerBarViewModel
                    )
                }
                NavigationBar(
                    onClickPlaylist = onClickPlaylist,
                    onClickLibrary = onClickLibrary,
                    onClickHome = onClickHome,
                    isHomeScreen = isHomeScreen
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen()
}