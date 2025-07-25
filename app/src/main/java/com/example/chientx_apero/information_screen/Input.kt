package com.example.chientx_apero.information_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Input(
    text: String = "",
    placeholder: String = "",
    modifier: Modifier = Modifier.Companion,
    size: Dp = 50.dp,
    onValueChange: (String) -> Unit,
    value: String,
    textError: String,
    enabledStatus: Boolean,
    keyboardType: KeyboardType
) {
    Column(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier.Companion.padding(10.dp),
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 14.sp,
                    color = Color(0xFF808080)
                )
            },
            enabled = enabledStatus,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.Companion
                .fillMaxWidth()
                .size(size),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Companion.Done
            )
        )
        Text(
            text = textError,
            color = Color.Companion.Red,
            modifier = Modifier.Companion.padding(top = 10.dp, start = 10.dp)
        )
    }
}