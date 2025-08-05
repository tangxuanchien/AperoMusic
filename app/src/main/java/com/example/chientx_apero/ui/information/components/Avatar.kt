package com.example.chientx_apero.ui.information.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.chientx_apero.R

@Composable
fun Avatar(
    context: Context,
    imageUri: Uri?,
    editStatus: Boolean,
    onClickSelectImage: () -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .height(165.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(imageUri ?: R.drawable.avatar)
                    .size(300, 300)
                    .build()
//                          Crop preview image 300x300
            ),
            contentDescription = null,
            modifier = Modifier.Companion
                .size(150.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentScale = ContentScale.Companion.Crop,
            alignment = Alignment.Companion.TopCenter
//                            Add alginment is crop image topcenter because default crop center image
        )
        if (editStatus) {
            Box(
                modifier = Modifier.Companion
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .align(Alignment.Companion.BottomCenter)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(R.drawable.camera),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(28.dp)
                        .align(Alignment.Companion.Center)
                        .clickable {
                            onClickSelectImage()
                        }
                )
            }
        }
    }
}