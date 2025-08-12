package com.example.chientx_apero.ui.home


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.Song
import com.example.chientx_apero.ui.components.NavigationBar
import com.example.chientx_apero.ui.home.components.HeaderHome
import com.example.chientx_apero.ui.home.components.Ranking
import com.example.chientx_apero.ui.home.components.TopAlbums
import com.example.chientx_apero.ui.home.components.TopArtists
import com.example.chientx_apero.ui.home.components.TopTracks
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
    onClickTopArtists: () -> Unit = {},
    onClickTopTracks: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val songs = mutableListOf<Song>(
        Song(1L, "Anh da on hon", "".toUri(), null, "MCK", "03:36", "Free Fire"),
        Song(2L, "Soda", "".toUri(), null, "tlinh", "03:36", "local"),
        Song(3L, "2h", "".toUri(), null, "Obito", "03:36", "local"),
        Song(4L, "Phong Ly", "".toUri(), null, "MCK", "03:36", "local"),
        Song(5L, "2323", "".toUri(), null, "MCK", "03:36", "local"),
        Song(6L, "okok", "".toUri(), null, "MCK", "03:36", "local"),
        Song(7L, "tutu", "".toUri(), null, "MCK", "03:36", "local"),
        Song(8L, "no way", "".toUri(), null, "MCK", "03:36", "local"),
        Song(9L, "no way 2", "".toUri(), null, "MCK", "03:36", "local"),
        Song(10L, "Huhu", "".toUri(), null, "MCK", "03:36", "local")
    )

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
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 10.dp)
                    .fillMaxSize()
            ) {
                Column {
                    HeaderHome(
                        onClickProfile = onClickProfile,
                        onClickSetting = {}
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
                NavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
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