// アプリ全体の見た目（テーマ）を定義する
package com.yuzu0923.miniveggarden.theme

// import
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GardenYellow,
    onPrimary = GardenGreen,

    secondary = GardenSalmon,
    onSecondary = GardenGreen,

    tertiary = GardenCream,
    onTertiary = GardenGreen,

    background = Color(0xFF292D10),
    onBackground = GardenCream,

    surface = Color(0xFF35391A),
    onSurface = GardenCream,

    error = GardenRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GardenGreen,
    onPrimary = Color.White,

    secondary = GardenYellow,
    onSecondary = GardenGreen,

    tertiary = GardenSalmon,
    onTertiary = GardenGreen,

    background = GardenBackground,
    onBackground = GardenGreen,

    surface = Color.White,
    onSurface = GardenGreen,

    error = GardenRed,
    onError = Color.White
)

@Composable
fun MiniVegGardenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}