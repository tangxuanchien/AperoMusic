package com.example.chientx_apero.signup_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chientx_apero.R
import com.example.chientx_apero.ui.theme.darkTheme

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    var currentTheme by remember { mutableStateOf(darkTheme) }
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF427880), Color(0xFF76D7E6)),
        start = Offset(400f, 0f),
        end = Offset(0f, 0f)
    )
    MaterialTheme(
        colorScheme = currentTheme.color,
        typography = currentTheme.typography
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Box {
                        Image(
                            painter = painterResource(R.drawable.logo_app),
                            contentDescription = null,
                            modifier = Modifier.size(250.dp)
                        )
                        BasicText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        brush = gradientBrush,
                                        fontSize = 40.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                ) {
                                    append("Apero Music")
                                }
                            },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}