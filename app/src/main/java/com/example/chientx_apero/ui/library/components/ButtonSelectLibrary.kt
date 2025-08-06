package com.example.chientx_apero.ui.library.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonSelectLibrary(
    modifier: Modifier = Modifier,
    onClickSelectLibrary: () -> Unit,
    isLocalLibrary: Boolean,
    text: String
) {
    Button(
        onClick = onClickSelectLibrary,
        colors =
            if (!isLocalLibrary) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.Companion.width(120.dp)
    ) {
        Text(
            text = text,
        )
    }
}