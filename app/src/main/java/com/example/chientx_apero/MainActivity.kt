package com.example.chientx_apero

import android.accessibilityservice.AccessibilityService
import android.app.Dialog
import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenDefault()
        }
    }
}

@Composable
fun Input(
    text: String = "",
    placeholder: String = "",
    modifier: Modifier = Modifier,
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
            modifier = Modifier.padding(10.dp)
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
            modifier = Modifier
                .fillMaxWidth()
                .size(size),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            )
        )
        Text(
            text = textError,
            color = Color.Red,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp)
        )
    }
}

@Composable
fun ScreenDefault() {
    val focusManager = LocalFocusManager.current
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var describe by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var universityError by remember { mutableStateOf("") }
    var describeError by remember { mutableStateOf("") }

    var showPopup by remember { mutableStateOf(false) }
    var editStatus by remember { mutableStateOf(false) }
    var enabledStatus by remember { mutableStateOf(false) }
    if (editStatus) {
        enabledStatus = true
    }
    Column(
        modifier = Modifier
            .background(Color(0x10007FFF))
            .clickable() { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (editStatus == true) {
            Text(
                text = "My Information".uppercase(),
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )
        } else {
            Box(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "My Information".uppercase(),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.edit_icon),
                    contentDescription = "Home Icon",
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            editStatus = true
                        }
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(width = 3.dp, color = Color.Black, shape = CircleShape)
        )
        Row(
        ) {
            Input(
                text = "Name",
                placeholder = "Enter your name...",
                modifier = Modifier.weight(1f),
                value = name,
                onValueChange = {
                    name = it
                    if (!Regex("^[\\p{L}\\s]+\$").matches(it)) {
                        nameError = "Invalid error"
                    } else if (Regex("^[\\p{L}\\s]+\$").matches(it) and !nameError.isEmpty()) {
                        nameError = ""
                    }
                },
                textError = nameError,
                enabledStatus = enabledStatus,
                keyboardType = KeyboardType.Text
            )
            Input(
                text = "Phone Number",
                placeholder = "Your phone number...",
                modifier = Modifier.weight(1f),
                value = phone,
                onValueChange = {
                    phone = it
                    if (!Regex("^\\d+\$").matches(it)) {
                        phoneError = "Invalid error"
                    } else if (Regex("^\\d+\$").matches(it) and !phoneError.isEmpty()) {
                        phoneError = ""
                    }
                },
                textError = phoneError,
                enabledStatus = enabledStatus,
                keyboardType = KeyboardType.Number
            )
        }
        Input(
            text = "University Name",
            placeholder = "Your university number...",
            value = university,
            onValueChange = {
                university = it
                if (!Regex("^[\\p{L}\\s]+\$").matches(it)) {
                    universityError = "Invalid error"
                } else if (Regex("^[\\p{L}\\s]+\$").matches(it) and !universityError.isEmpty()) {
                    universityError = ""
                }
            },
            textError = universityError,
            enabledStatus = enabledStatus,
            keyboardType = KeyboardType.Text
        )
        Input(
            text = "Describe Yourself",
            placeholder = "Enter a description about yourself...",
            modifier = Modifier
                .fillMaxWidth(),
            size = 200.dp,
            value = describe,
            onValueChange = {
                describe = it
                if (!Regex("^[A-Za-z0-9\\s]+\$").matches(it)) {
                    universityError = "Invalid error"
                } else if (Regex("^[A-Za-z0-9\\s]+\$").matches(it) and !universityError.isEmpty()) {
                    universityError = ""
                }
            },
            textError = describeError,
            enabledStatus = enabledStatus,
            keyboardType = KeyboardType.Text
        )
        if (editStatus) {
            Button(
                onClick = {
                    if (name.isEmpty()) {
                        nameError = "Empty name"
                    } else if (phone.isEmpty()) {
                        phoneError = "Empty phone"
                    } else if (university.isEmpty()) {
                        universityError = "Empty university"
                    } else if (describe.isEmpty()) {
                        describeError = "Empty describe"
                    } else {
                        if (nameError.isEmpty() and phoneError.isEmpty() and universityError.isEmpty() and describeError.isEmpty()) {
                            showPopup = true
                        }
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(bottom = 40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Submit",
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 35.dp)
                )
            }
        }
    }
    if (showPopup) {
        PopupLayout(
            onDismiss = { showPopup = false }
        )
        editStatus = false
        enabledStatus = false
    }
}

@Composable
fun PopupLayout(onDismiss: () -> Unit) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .size(350.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 50.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.success),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
                Text(
                    text = "Success!",
                    color = Color(0xFF08A045),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(40.dp)
                )
                Text(
                    text = "Your information has been updated!",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(2000)
        onDismiss()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    ScreenDefault()
}