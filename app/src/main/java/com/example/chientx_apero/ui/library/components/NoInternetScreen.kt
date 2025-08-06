package com.example.chientx_apero.ui.library.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onClickTryAgain: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = modifier
            .width(250.dp)
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.no_internet),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "No internet connection, please check your connection again",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Companion.Center,
            fontSize = 20.sp,
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(12.dp)
        )
        Button(
            onClick = onClickTryAgain
        ) {
            Text(
                text = "Try again"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNoInternetScreen() {
    NoInternetScreen(
        onClickTryAgain = {}
    )
}