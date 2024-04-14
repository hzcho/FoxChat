package com.example.foxchat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFffb689),
    onPrimary = Color(0xff512300),
    primaryContainer = Color(0xff743500),
    onPrimaryContainer = Color(0xffffdbc8),
    //inversePrimary = Color(0xFF984800),
    secondary = Color(0xffe5bfa9),
    onSecondary = Color(0xff432b1c),
    secondaryContainer = Color(0xff5c4130),
    onSecondaryContainer = Color(0xffffdbc8),
    tertiary = Color(0xffcbc992),
    onTertiary = Color(0xff323209),
    tertiaryContainer = Color(0xff49491e),
    onTertiaryContainer = Color(0xffe7e5ac),
    background = Color(0xff201a17),
    onBackground = Color(0xffece0da),
    surface = Color(0xff201a17),
    onSurface = Color(0xffece0da),
    surfaceVariant = Color(0xff52443c),
    onSurfaceVariant = Color(0xffd7c2b8),
//    surfaceTint = Color(0xff),
//    inverseSurface = Color(0xff),
//    inverseOnSurface = Color(0xff),
    error = Color(0xffffb4ab),
    onError = Color(0xff690005),
    errorContainer = Color(0xff93000a),
    onErrorContainer = Color(0xffffdad6),
    outline = Color(0xff9f8d83),
    //outlineVariant = Color(0xff),
    //scrim = Color(0xff)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFff8d3b),
    onPrimary = Color(0xffffffff),
    primaryContainer = Color(0xffffdbc8),
    onPrimaryContainer = Color(0xff321300),
    //inversePrimary = Color(0xFF984800),
    secondary = Color(0xff755846),
    onSecondary = Color(0xffffffff),
    secondaryContainer = Color(0xffffdbc8),
    onSecondaryContainer = Color(0xff2b1709),
    tertiary = Color(0xff616133),
    onTertiary = Color(0xffffffff),
    tertiaryContainer = Color(0xffe7e5ac),
    onTertiaryContainer = Color(0xff1d1d00),
    background = Color(0xfffffbff),
    onBackground = Color(0xff201a17),
    surface = Color(0xfffffbff),
    onSurface = Color(0xff201a17),
    surfaceVariant = Color(0xfff4ded3),
    onSurfaceVariant = Color(0xff52443c),
//    surfaceTint = Color(0xff),
//    inverseSurface = Color(0xff),
//    inverseOnSurface = Color(0xff),
    error = Color(0xffba1a1a),
    onError = Color(0xffffffff),
    errorContainer = Color(0xffffdad6),
    onErrorContainer = Color(0xff410002),
    outline = Color(0xff84746b),
    //outlineVariant = Color(0xff),
    //scrim = Color(0xff)

)

@Composable
fun FoxChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}