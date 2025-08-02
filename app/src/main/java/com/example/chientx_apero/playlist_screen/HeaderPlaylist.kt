package com.example.chientx_apero.playlist_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun HeaderPlaylist(
    stateMyPlaylist: Boolean = false,
    stateSortView: Boolean = false,
    stateGridView: Boolean = false,
    titlePlaylist: String = "My playlist",
    onToggleSortView: () -> Unit = {},
    onToggleGridView: () -> Unit = {},
    onClickAddMyPlaylist: () -> Unit = {},
) {
    Box(
        modifier = Modifier.Companion.padding(vertical = 10.dp)
    ) {
        if (stateSortView) {
            Icon(
                imageVector = ImageVector.Companion.vectorResource(R.drawable.cancel),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.Companion
                    .size(20.dp)
                    .align(Alignment.Companion.CenterStart)
                    .clickable {
                        onToggleSortView()
                    }
            )
        }
        Text(
            text = if (!stateSortView) {
                titlePlaylist
            } else {
                "Sorting"
            },
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Companion.Center,
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 24.sp,
            modifier = Modifier.Companion
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier.Companion
                .align(Alignment.Companion.BottomEnd)
        ) {
            if (stateMyPlaylist) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.add_playlist),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.Companion
                        .size(20.dp)
                        .clickable {
                            onClickAddMyPlaylist()
                        }
                )
            } else {
                if (stateGridView) {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.column),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.Companion
                            .size(24.dp)
                            .clickable { onToggleGridView() }
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.grid),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.Companion
                            .size(24.dp)
                            .clickable { onToggleGridView() }
                    )
                }
                Spacer(modifier = Modifier.Companion.padding(8.dp))
                if (stateSortView) {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.Companion
                            .size(20.dp)
                            .clickable {
                                onToggleSortView()
                            }
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.sort),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.Companion
                            .size(26.dp)
                            .clickable {
                                onToggleSortView()
                            }
                    )
                }
            }
        }
    }
}