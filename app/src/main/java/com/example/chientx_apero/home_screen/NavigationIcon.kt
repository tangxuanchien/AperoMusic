package com.example.chientx_apero.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun NavigationIcon(
    modifier: Modifier = Modifier.Companion,
    icon: Int,
    text: String,
    onClick: () -> Unit,
    isScreen: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
            Icon(
                imageVector = ImageVector.Companion.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.Companion
                    .size(26.dp)
                    .clickable(
                        onClick = onClick
                    ),
                tint = if(isScreen){Color(0xFFA2EFFF)} else {Color.White}
            )
            Text(
                text = text,
                color = if(isScreen){Color(0xFFA2EFFF)} else {Color.White}
            )
    }
}