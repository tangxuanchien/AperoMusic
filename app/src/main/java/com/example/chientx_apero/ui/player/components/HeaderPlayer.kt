package com.example.chientx_apero.ui.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun HeaderPlayer(
    onClickBackSelected: () -> Unit,
    onClickBackUnSelected: () -> Unit
) {
    Box(
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.back),
            contentDescription = "Back Home",
            modifier = Modifier.Companion
                .size(24.dp)
                .align(Alignment.Companion.CenterStart)
                .clickable {
                    onClickBackSelected()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Now Playing",
            fontSize = 24.sp,
            modifier = Modifier.Companion
                .align(Alignment.Companion.Center)
                .fillMaxWidth(),
            textAlign = TextAlign.Companion.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = ImageVector.Companion.vectorResource(id = R.drawable.cancel),
            contentDescription = "UnSelected",
            modifier = Modifier.Companion
                .size(22.dp)
                .align(Alignment.Companion.CenterEnd)
                .clickable {
                    onClickBackUnSelected()
                },
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevHeaderPlayer() {
    HeaderPlayer(
        onClickBackSelected = {},
        onClickBackUnSelected = {}
    )
}