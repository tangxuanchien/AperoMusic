package com.example.chientx_apero.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.room_db.entity.User

@Composable
fun HeaderHome(
    modifier: Modifier = Modifier.Companion,
    onClickProfile: () -> Unit,
    onClickSettings: () -> Unit,
    user: User?
) {
    Box(
        modifier = Modifier.Companion
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterStart)
                .padding(start = 10.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    user?.avatar ?: R.drawable.avatar
                ),
                contentDescription = null,
                modifier = Modifier.Companion
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clickable(
                        onClick = onClickProfile
                    ),
                contentScale = ContentScale.Companion.Crop,
                alignment = Alignment.Companion.TopCenter
            )
            Box(
                modifier = Modifier.Companion.padding(start = 14.dp)
            ) {
                Text(
                    text = "Welcome back!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = user?.name ?: "New User",
                    fontSize = 12.sp,
                    modifier = Modifier.Companion.padding(top = 14.dp),
                    fontWeight = FontWeight.Companion.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.settings),
            contentDescription = null,
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterEnd)
                .size(28.dp)
                .clickable(
                    onClick = onClickSettings
                ),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}