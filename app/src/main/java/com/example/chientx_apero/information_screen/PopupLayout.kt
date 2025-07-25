package com.example.chientx_apero.information_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.chientx_apero.R
import kotlinx.coroutines.delay

@Composable
fun PopupLayout(onDismiss: () -> Unit) {
    Popup(
        alignment = Alignment.Companion.Center,
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier.Companion
                .size(350.dp)
                .background(Color.Companion.White, shape = RoundedCornerShape(16.dp))
                .border(
                    1.dp,
                    Color.Companion.LightGray,
                    androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Companion.Center
        ) {

            Column(
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                modifier = Modifier.Companion
                    .padding(vertical = 20.dp, horizontal = 50.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.success),
                    contentDescription = null,
                    modifier = Modifier.Companion.size(130.dp)
                )
                Text(
                    text = "Success!",
                    color = Color(0xFF08A045),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion
                        .padding(40.dp)
                )
                Text(
                    text = "Your information has been updated!",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Companion.Center
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        onDismiss()
    }
}