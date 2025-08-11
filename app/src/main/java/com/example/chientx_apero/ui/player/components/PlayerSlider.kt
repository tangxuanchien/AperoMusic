package com.example.chientx_apero.ui.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.room_db.entity.Song

@Composable
fun PlayerSlider(
    modifier: Modifier = Modifier,
    song: Song?,
    onValueChangeFinished: () -> Unit,
    onValueChange: (Float) -> Unit,
    currentTime: String,
    value: Float
) {
//    Slider(
//        value = value,
//        onValueChange = {
//            onValueChange()
//        },
//        onValueChangeFinished = {
//            onValueChangeFinished()
//        },
//        valueRange = 0f..1f,
//        colors = SliderDefaults.colors(
//            activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
//            inactiveTrackColor = Color.Companion.DarkGray
//        ),
//        modifier = Modifier.Companion
//            .fillMaxWidth()
//            .height(6.dp)
//    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.Companion
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = currentTime,
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 14.sp,
            color = Color(0x99CCCCCC)
        )
        Text(
            text = song!!.duration,
            fontWeight = FontWeight.Companion.Bold,
            fontSize = 14.sp,
            color = Color(0x99CCCCCC)
        )
    }
}