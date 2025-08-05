package com.example.chientx_apero.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun ItemGrid(
    song: Song,
    onOpenMenu: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier.Companion
                .width(160.dp)
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier.Companion
                    .padding(8.dp)
                    .background(
                        Color(0x88000000),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(34.dp)
                    .align(Alignment.Companion.TopEnd)
                    .zIndex(1f)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.option),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.Companion
                        .size(20.dp)
                        .clickable {
                            onOpenMenu()
                        }
                )
            }
            Image(
                painter = rememberAsyncImagePainter(song.image),
                contentDescription = null,
                modifier = Modifier.Companion
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(14.dp))
                    .size(160.dp)
                    .align(Alignment.Companion.TopCenter)
            )
            Box(
                modifier = Modifier.Companion.align(Alignment.Companion.BottomCenter)
            ) {
                Column(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalAlignment = Alignment.Companion.CenterHorizontally
                ) {
                    Text(
                        text = song.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Companion.Ellipsis,
                        modifier = Modifier.Companion
                            .padding(top = 2.dp)
                    )
                    Text(
                        modifier = Modifier.Companion.padding(top = 4.dp),
                        text = song.artist,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Companion.Ellipsis,
                        color = Color(0x99CCCCCC)
                    )
                    Text(
                        modifier = Modifier.Companion.padding(top = 8.dp),
                        text = song.duration,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
            DropdownItemApp(
                expanded = expanded,
                onClick = onClick,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .padding(top = 30.dp)
            )
        }
    }
}