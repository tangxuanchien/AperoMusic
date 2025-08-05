package com.example.chientx_apero.ui.my_playlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chientx_apero.model.PlaylistModel
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun PopupAddMyPlaylist(
    onDismissRequest: () -> Unit,
    onCreateMyPlaylist: () -> Unit,
    currentTheme: ThemeData = darkTheme,
    onValueChange: (String) -> Unit,
    value: String,
    playlistModels: SnapshotStateList<PlaylistModel>
    = mutableStateListOf<PlaylistModel>(),
) {
    MaterialTheme(
        colorScheme = currentTheme.color
    ) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Box(
                modifier = Modifier.Companion
                    .height(220.dp)
                    .width(350.dp)
                    .background(
                        MaterialTheme.colorScheme.inverseOnSurface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        1.dp,
                        Color.Companion.LightGray,
                        androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.Companion.CenterHorizontally,
                    modifier = Modifier.Companion
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "New playlist",
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Companion.Center,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                    )
                    if (playlistModels.isEmpty()) {
                        TextField(
                            placeholder = { Text("Give your playlist a title") },
                            value = value,
                            onValueChange = onValueChange,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 2.dp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "Cancel",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {
                                        onDismissRequest()
                                    }
                            )
                            VerticalDivider(
                                color = MaterialTheme.colorScheme.primary,
                                thickness = 2.dp,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                            Text(
                                text = "Create",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {
                                        onCreateMyPlaylist()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPopUp() {
    PopupAddMyPlaylist(
        onDismissRequest = {},
        onCreateMyPlaylist = {},
        onValueChange = {},
        value = ""
    )
}