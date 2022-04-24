package com.payconiq.android.github.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.payconiq.android.github.common.R

private val letterSpacing = .8F.sp

private val lato = FontFamily(
    Font(R.font.lato_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.lato_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.lato_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.lato_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.lato_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.lato_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.lato_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.lato_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.lato_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.lato_blackitalic, FontWeight.Black, FontStyle.Italic)
)

private val openSans = FontFamily(
    Font(R.font.lato_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.lato_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.opensans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.opensans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.opensans_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.opensans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.opensans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.opensans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.opensans_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.opensans_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic)
)

val Typography = Typography(
    defaultFontFamily = openSans,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = letterSpacing
    ),
    h1 = TextStyle(
        fontFamily = lato,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = lato,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = lato,
        fontSize = 20.sp,
        letterSpacing = letterSpacing
    ),
    h4 = TextStyle(
        fontFamily = lato,
        fontSize = 18.sp,
        letterSpacing = letterSpacing
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = letterSpacing
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = letterSpacing
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = letterSpacing
    )
)
