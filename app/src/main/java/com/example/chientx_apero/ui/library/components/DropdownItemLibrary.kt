package com.example.chientx_apero.playlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.chientx_apero.R

@Composable
fun DropdownItemLibrary(
    expanded: Boolean,
    onClick: () -> Unit,
    onShare: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.Companion.Black.copy(0.8f), shape = RoundedCornerShape(15.dp))
    ) {
        DropdownMenu(
            modifier = Modifier.Companion
                .background(Color.Companion.Black.copy(0.8f))
                .padding(horizontal = 10.dp),
            expanded = expanded,
            onDismissRequest = { onDismissRequest() }
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.add),
                        contentDescription = null,
                        tint = Color.Companion.White
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.add_to_playlist),
                        color = Color.Companion.White
                    )
                },
                onClick = {
                    onClick()
                }
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Companion.White.copy(alpha = 0.1f),
                modifier = Modifier.Companion
                    .width(200.dp)
                    .padding(start = 50.dp)
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.Companion.vectorResource(R.drawable.share),
                        contentDescription = null,
                        tint = Color.Companion.White
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.share),
                        color = Color.Companion.White
                    )
                },
                onClick = {onShare()}
            )
        }
    }
}