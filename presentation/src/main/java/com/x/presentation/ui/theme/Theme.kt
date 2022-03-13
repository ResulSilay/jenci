package com.x.presentation.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val LightThemeColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
)

private val DarkThemeColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
)

val LocalColors = staticCompositionLocalOf {
    DarkThemeColors
}

val LocalDimensions = staticCompositionLocalOf {
    DefaultDimensions
}

val LocalTypography = staticCompositionLocalOf {
    Typography
}

val LocalShapes = staticCompositionLocalOf {
    Shapes
}

@Composable
fun ProvideColors(colors: ColorScheme, content: @Composable () -> Unit) {
    val colorSchemeSet = remember { colors }
    CompositionLocalProvider(LocalColors provides colorSchemeSet, content = content)
}

@Composable
fun ProvideDimensions(dimensions: Dimensions, content: @Composable () -> Unit) {
    val dimensionsSet = remember { dimensions }
    CompositionLocalProvider(LocalDimensions provides dimensionsSet, content = content)
}

@Composable
fun ProvideShapes(shapes: Shape, content: @Composable () -> Unit) {
    val shapesSet = remember { shapes }
    CompositionLocalProvider(LocalShapes provides shapesSet, content = content)
}

@Composable
fun ProvideRippleEffect(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalIndication provides rememberRipple(), content = content)
}

@Composable
fun ProvideCircleRippleEffect(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalIndication provides rememberRipple(bounded = false), content = content)
}

@Composable
fun JenciTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        DarkThemeColors
    }

    val dimensions = DefaultDimensions
    val typography = Typography

    ProvideColors(colors = colors) {
        ProvideDimensions(dimensions = dimensions) {
            MaterialTheme(
                colorScheme = colors,
                typography = typography,
            ) {
                ProvideRippleEffect(
                    content = content
                )
            }
        }
    }
}

object Theme {
    val colors: ColorScheme
        @Composable
        get() = LocalColors.current
    val dimens: Dimensions
        @Composable
        get() = LocalDimensions.current
    val typography: Typography
        @Composable
        get() = LocalTypography.current
    val shapes: Shape
        @Composable
        get() = LocalShapes.current
}