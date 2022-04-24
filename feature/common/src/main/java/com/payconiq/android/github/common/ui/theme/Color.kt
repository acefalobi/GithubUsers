package com.payconiq.android.github.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val PrimaryColorAlpha = Color(0x229C4146)
val PrimaryColor = Color(0xFF9C4146)

val SnackbarActionColor: Color
    @Composable
    get() {
        val colors = MaterialTheme.colors
        return if (colors.isLight) {
            val primary = colors.primary
            val overlayColor = colors.surface.copy(alpha = 0.6f)

            overlayColor.compositeOver(primary)
        } else {
            colors.primary
        }
    }
