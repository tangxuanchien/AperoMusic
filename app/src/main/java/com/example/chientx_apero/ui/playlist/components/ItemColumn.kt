package com.example.chientx_apero.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.SongModel

@Composable
fun ItemColumn(
    songModel: SongModel,
    onOpenMenu: () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(songModel.image),
            contentDescription = null,
            modifier = Modifier.Companion
                .clip(RoundedCornerShape(10.dp))
                .size(64.dp)
        )
        Box(
            modifier = Modifier.Companion
                .padding(start = 12.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.Companion.align(Alignment.Companion.CenterStart)
            ) {
                Text(
                    text = songModel.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion.padding(bottom = 4.dp)
                )
                Text(
                    text = songModel.artist,
                    fontWeight = FontWeight.Companion.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Row(
                modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd)
            ) {
                Text(
                    text = songModel.duration,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.Companion.padding(6.dp))
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
            DropdownItemApp(
                expanded = expanded,
                onClick = onClick,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd)
            )
        }
    }
}