package com.example.chientx_apero.information_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonInformation(
    onClickButtonInformation: () -> Unit,
    editStatus: Boolean
) {
    if (editStatus) {
        Button(
            onClick = {
                onClickButtonInformation()

            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.Companion
                .padding(bottom = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            )
        ) {
            Text(
                text = "Submit",
                modifier = Modifier.Companion
                    .padding(vertical = 15.dp, horizontal = 35.dp)
            )
        }
    }
}