package com.example.chientx_apero.ui.player.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun SongInfo(
    modifier: Modifier = Modifier,
    image: Bitmap?,
    artist: String,
    name: String
) {
    Image(
        painter = rememberAsyncImagePainter(image ?: R.drawable.avatar),
        contentDescription = "Image Song",
        modifier = Modifier.Companion
            .size(375.dp)
            .padding(top = 20.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
    )
    Text(
        text = name,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 26.sp,
        fontWeight = FontWeight.Companion.Bold,
        maxLines = 1,
        overflow = TextOverflow.Companion.Ellipsis,
        modifier = Modifier.Companion
            .padding(top = 2.dp, start = 4.dp)
    )
    Text(
        modifier = Modifier.Companion.padding(
            top = 5.dp,
            start = 4.dp,
            bottom = 20.dp
        ),
        text = artist,
        fontWeight = FontWeight.Companion.Bold,
        fontSize = 18.sp,
        maxLines = 1,
        overflow = TextOverflow.Companion.Ellipsis,
        color = Color(0x99CCCCCC)
    )
}