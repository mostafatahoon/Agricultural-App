package com.example.agriculturalapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = VibrantLeafGreen,
    onPrimary = DarkFoliage,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF244E26),
    onPrimaryContainer = SoftSage,
    secondary = EarthMoss,
    onSecondary = DarkFoliage,
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF2A3326),
    onSecondaryContainer = SoftSage,
    tertiary = SoftSage,
    onTertiary = DarkFoliage,
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFF30392C),
    onTertiaryContainer = SoftSage,
    background = androidx.compose.ui.graphics.Color(0xFF11150F),
    onBackground = SoftSage,
    surface = androidx.compose.ui.graphics.Color(0xFF171D14),
    onSurface = SoftSage,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF252D21),
    onSurfaceVariant = EarthMoss,
    outline = androidx.compose.ui.graphics.Color(0xFF5C6656),
    outlineVariant = androidx.compose.ui.graphics.Color(0xFF363E32),
    inverseOnSurface = DarkFoliage,
    inverseSurface = SoftSage,
    inversePrimary = DeepForestGreen,
    error = androidx.compose.ui.graphics.Color(0xFFFFB4AB),
    onError = androidx.compose.ui.graphics.Color(0xFF690005),
    errorContainer = androidx.compose.ui.graphics.Color(0xFF93000A),
    onErrorContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6),
    surfaceTint = VibrantLeafGreen,
    scrim = androidx.compose.ui.graphics.Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = DeepForestGreen,
    onPrimary = SurfaceWhite,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFDCEAD6),
    onPrimaryContainer = DarkFoliage,
    secondary = VibrantLeafGreen,
    onSecondary = SurfaceWhite,
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFD5EDD4),
    onSecondaryContainer = DarkFoliage,
    tertiary = EarthMoss,
    onTertiary = DarkFoliage,
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFFE8EFE3),
    onTertiaryContainer = DarkFoliage,
    background = SoftSage,
    onBackground = DarkFoliage,
    surface = SurfaceWhite,
    onSurface = DarkFoliage,
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE6EEE1),
    onSurfaceVariant = MediumEmphasis,
    outline = EarthMoss,
    outlineVariant = androidx.compose.ui.graphics.Color(0xFFD7E0D2),
    inverseOnSurface = SurfaceWhite,
    inverseSurface = DarkFoliage,
    inversePrimary = VibrantLeafGreen,
    error = androidx.compose.ui.graphics.Color(0xFFB3261E),
    onError = SurfaceWhite,
    errorContainer = androidx.compose.ui.graphics.Color(0xFFF9DEDC),
    onErrorContainer = androidx.compose.ui.graphics.Color(0xFF410E0B),
    surfaceTint = DeepForestGreen,
    scrim = androidx.compose.ui.graphics.Color.Black
)

@Composable
fun AgriculturalAPPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}