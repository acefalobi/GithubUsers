package com.payconiq.android.github.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorAlpha,

    onSecondary = Color.White
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryColorAlpha,

    onSecondary = PrimaryColor,
)

@Composable
fun GithubUsersTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(PrimaryColorAlpha)
    val colors = if (darkTheme) {
        systemUiController.systemBarsDarkContentEnabled = false
        DarkColorPalette
    } else {
        systemUiController.systemBarsDarkContentEnabled = true
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun topAppBarTheme(): TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
    containerColor = MaterialTheme.colors.primaryVariant,
    titleContentColor = MaterialTheme.colors.onSecondary,
    actionIconContentColor = MaterialTheme.colors.onSecondary,
    navigationIconContentColor = MaterialTheme.colors.onSecondary,
)

@Composable
fun buttonTheme(): ButtonColors = ButtonDefaults.buttonColors(
    MaterialTheme.colors.primaryVariant,
    MaterialTheme.colors.onSecondary,
)
