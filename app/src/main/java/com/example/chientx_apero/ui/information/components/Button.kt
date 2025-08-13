package com.example.chientx_apero.ui.information.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun ButtonAction(
    onClickButtonInformation: () -> Unit = {},
    onClickLogOut: () -> Unit = {},
    editStatus: Boolean,
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
                text = stringResource(R.string.submit),
                modifier = Modifier.Companion
                    .padding(vertical = 15.dp, horizontal = 35.dp)
            )
        }
    } else {
        Button(
            onClick = {
                onClickLogOut()
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.Companion
                .padding(bottom = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(id = R.drawable.logout),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.errorContainer
                )
                Text(
                    text = stringResource(R.string.log_out),
                    modifier = Modifier.Companion
                        .padding(vertical = 15.dp, horizontal = 35.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                    fontSize = 18.sp
                )
            }
        }
    }
}