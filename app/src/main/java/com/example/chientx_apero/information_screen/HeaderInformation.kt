package com.example.chientx_apero.information_screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.theme.ThemeData
import com.example.chientx_apero.ui.theme.lightTheme

@Composable
fun HeaderInformation(
    currentTheme: ThemeData,
    editStatus: Boolean,
    onToggleEditStatus: () -> Unit,
    onToggleCurrentTheme: () -> Unit
) {
    Box(
        modifier = Modifier.Companion.padding(10.dp)
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(
                id = if (currentTheme == lightTheme) {
                    R.drawable.dark_theme
                } else R.drawable.light_theme
            ),
            contentDescription = "Home Icon",
            modifier = Modifier.Companion
                .size(28.dp)
                .align(Alignment.Companion.CenterStart)
                .clickable {
                    onToggleCurrentTheme()
                },
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "My Information".uppercase(),
            fontSize = 24.sp,
            modifier = Modifier.Companion
                .align(Alignment.Companion.Center)
                .fillMaxWidth(),
            textAlign = TextAlign.Companion.Center,
            color = MaterialTheme.colorScheme.primary
        )
        if (!editStatus) {
            Icon(
                imageVector = ImageVector.Companion.vectorResource(id = R.drawable.edit_icon),
                contentDescription = null,
                modifier = Modifier.Companion
                    .size(28.dp)
                    .align(Alignment.Companion.CenterEnd)
                    .clickable {
                        onToggleEditStatus()
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}