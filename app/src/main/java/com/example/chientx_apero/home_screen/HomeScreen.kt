package com.example.chientx_apero.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.theme.darkTheme


@Composable
fun HomeScreen(
    onClickProfile: () -> Unit,
    onClickPlaylist: () -> Unit,
    onClickLibrary: () -> Unit,
    isHomeScreen: Boolean = false,
    onClickBack: () -> Unit
) {
    var currentTheme by remember { mutableStateOf(darkTheme) }
    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.settings),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.TopEnd)
                        .size(36.dp)
                        .clickable(
                            onClick = onClickProfile
                        )
                )
                Text(
                    text = "Home Screen",
                    fontSize = 50.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                NavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onClickPlaylist = onClickPlaylist,
                    onClickLibrary = onClickLibrary,
                    isHomeScreen = isHomeScreen
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    HomeScreen(
        onClickProfile = {},
        onClickPlaylist = {},
        onClickBack = {},
        onClickLibrary = {}
    )
}