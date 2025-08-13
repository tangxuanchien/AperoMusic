package com.example.chientx_apero.ui.settings

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.MainActivity
import com.example.chientx_apero.R
import com.example.chientx_apero.model.AppCache
import com.example.chientx_apero.ui.theme.darkTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var language by remember { mutableIntStateOf(AppCache.language) }
    var locale by remember { mutableStateOf(AppCache.locale) }
    val context = LocalContext.current
    val activity = context as? Activity
    MaterialTheme(
        colorScheme = darkTheme.color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 18.dp, horizontal = 12.dp)
        ) {
            Box(
                modifier = Modifier.Companion.padding(10.dp)
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(
                        R.drawable.back
                    ),
                    contentDescription = "Home Icon",
                    modifier = Modifier.Companion
                        .size(28.dp)
                        .align(Alignment.Companion.CenterStart)
                        .clickable {
                            onClickBack()
                        },
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.settings),
                    fontSize = 24.sp,
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.Center)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Companion.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(id = R.drawable.done),
                    contentDescription = null,
                    modifier = Modifier.Companion
                        .size(24.dp)
                        .align(Alignment.Companion.CenterEnd)
                        .clickable {
                            AppCache.language = language
                            AppCache.locale = locale
                            activity?.recreate()
                            onClickBack()
                        },
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Box(
                modifier = Modifier.Companion
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = ImageVector.Companion.vectorResource(
                        R.drawable.translate
                    ),
                    contentDescription = "Translate",
                    modifier = Modifier.Companion
                        .size(28.dp)
                        .align(Alignment.Companion.CenterStart),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.language),
                    fontSize = 20.sp,
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.CenterStart)
                        .padding(start = 50.dp),
                    textAlign = TextAlign.Companion.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(language),
                    fontSize = 16.sp,
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.CenterEnd)
                        .padding(end = 4.dp)
                        .clickable {
                            expanded = true
                        },
                    textAlign = TextAlign.Companion.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                DropdownMenu(
                    modifier = Modifier.Companion
                        .background(Color.Companion.Black.copy(0.8f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    offset = DpOffset(x = 230.dp, y = 10.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.english),
                                color = Color.Companion.White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            language = R.string.english
                            locale = "en"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.korean),
                                color = Color.Companion.White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            language = R.string.korean
                            locale = "ko"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.french),
                                color = Color.Companion.White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            language = R.string.french
                            locale = "fr"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.vietnamese),
                                color = Color.Companion.White,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {
                            language = R.string.vietnamese
                            locale = "vi"
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevSettingsScreen() {
    SettingsScreen()
}