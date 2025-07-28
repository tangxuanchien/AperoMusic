package com.example.chientx_apero.information_screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R

@Composable
fun Input(
    text: String = "",
    placeholder: String = "",
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    onValueChange: (String) -> Unit,
    value: String,
    textError: String = "",
    leadingIcon: Int = 0,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabledStatus: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(
        modifier = modifier
    ) {
        if (!text.isEmpty()) {
            Text(
                text = text.uppercase(),
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        OutlinedTextField(
            visualTransformation = visualTransformation,
            leadingIcon =
                if (leadingIcon != 0) {
                    {
                        Icon(
                            imageVector = ImageVector.vectorResource(leadingIcon),
                            contentDescription = "Leading Icon",
                            modifier = Modifier.size(15.dp),
                            tint = Color(0xFF808080)
                        )
                    }
                } else null,
            trailingIcon = trailingIcon,
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
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(size),
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
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun PreviewTest(modifier: Modifier = Modifier) {
    Input(
        placeholder = "Username",
        value = "",
        onValueChange = {
        },
        leadingIcon = R.drawable.outline_visibility_off_24,
        textError = "Error",
    )
}