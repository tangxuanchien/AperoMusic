package com.example.chientx_apero.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun Ranking(modifier: Modifier = Modifier.Companion) {
    Row(
        modifier = Modifier.Companion.padding(vertical = 10.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = ImageVector.Companion.vectorResource(R.drawable.rank),
            contentDescription = null,
            modifier = Modifier.Companion
                .size(22.dp),
            tint = Color(0XFFFFF500)
        )
        Text(
            text = stringResource(R.string.rankings),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.padding(start = 10.dp)
        )
    }
}