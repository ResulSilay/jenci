package com.x.presentation.ui.theme

import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF00658f)
val md_theme_light_onPrimary = Color(0xFFffffff)
val md_theme_light_primaryContainer = Color(0xFFc5e7ff)
val md_theme_light_onPrimaryContainer = Color(0xFF001e2e)
val md_theme_light_secondary = Color(0xFF4f616e)
val md_theme_light_onSecondary = Color(0xFFffffff)
val md_theme_light_secondaryContainer = Color(0xFFd2e5f5)
val md_theme_light_onSecondaryContainer = Color(0xFF0b1d28)
val md_theme_light_tertiary = Color(0xFF62597c)
val md_theme_light_onTertiary = Color(0xFFffffff)
val md_theme_light_tertiaryContainer = Color(0xFFe9ddff)
val md_theme_light_onTertiaryContainer = Color(0xFF1e1635)
val md_theme_light_error = Color(0xFFba1b1b)
val md_theme_light_errorContainer = Color(0xFFffdad4)
val md_theme_light_onError = Color(0xFFffffff)
val md_theme_light_onErrorContainer = Color(0xFF410001)
val md_theme_light_background = Color(0xFFfbfcff)
val md_theme_light_onBackground = Color(0xFF191c1e)
val md_theme_light_surface = Color(0xFFfbfcff)
val md_theme_light_onSurface = Color(0xFF191c1e)
val md_theme_light_surfaceVariant = Color(0xFFdde3ea)
val md_theme_light_onSurfaceVariant = Color(0xFF41484d)
val md_theme_light_outline = Color(0xFF71787e)
val md_theme_light_inverseOnSurface = Color(0xFFf0f1f4)
val md_theme_light_inverseSurface = Color(0xFF2e3133)
val md_theme_light_inversePrimary = Color(0xFF80cfff)

val md_theme_dark_primary = Color(0xFF80cfff)
val md_theme_dark_onPrimary = Color(0xFF00344c)
val md_theme_dark_primaryContainer = Color(0xFF004c6d)
val md_theme_dark_onPrimaryContainer = Color(0xFFc5e7ff)
val md_theme_dark_secondary = Color(0xFFb7c9d8)
val md_theme_dark_onSecondary = Color(0xFF252A2E)
val md_theme_dark_secondaryContainer = Color(0xFF374956)
val md_theme_dark_onSecondaryContainer = Color(0xFFd2e5f5)
val md_theme_dark_tertiary = Color(0xFFcdc1e9)
val md_theme_dark_onTertiary = Color(0xFF332b4b)
val md_theme_dark_tertiaryContainer = Color(0xFF4b4263)
val md_theme_dark_onTertiaryContainer = Color(0xFFe9ddff)
val md_theme_dark_error = Color(0xFFffb4a9)
val md_theme_dark_errorContainer = Color(0xFF930006)
val md_theme_dark_onError = Color(0xFF680003)
val md_theme_dark_onErrorContainer = Color(0xFFffdad4)
val md_theme_dark_background = Color(0xFF191c1e)
val md_theme_dark_onBackground = Color(0xFFe1e2e5)
val md_theme_dark_surface = Color(0xFF191c1e)
val md_theme_dark_onSurface = Color(0xFFe1e2e5)
val md_theme_dark_surfaceVariant = Color(0xFF21323e)
val md_theme_dark_onSurfaceVariant = Color(0xFFc1c7ce)
val md_theme_dark_outline = Color(0xFF212529)
val md_theme_dark_inverseOnSurface = Color(0xFF1C2A33)
val md_theme_dark_inverseSurface = Color(0xFFe1e2e5)
val md_theme_dark_inversePrimary = Color(0xFF00658f)

val backgroundPrimary = Color(0xFF222B31)

enum class BuildStatusColors(val color: Color) {
    NONE(Color(0xFF5C5C5C)),
    SUCCESSFUL(Color(0xFF02B884)),
    FAILED(Color(0xFFba1b1b)),
    UNSTABLE(Color(0xFFFFC107)),
    PENDING(Color(0xFFCACACA)),
    DISABLED(Color(0xFF696969)),
    ABORTED(Color(0xFF926253)),
    NOT_BUILT(Color(0xFF131313));

    companion object {
        fun BuildStatusColors.get(): Color = this.color
    }
}

enum class JobStatusColors(val color: Color) {
    PROJECT(Color(0xFF949494)),
    FOLDER(Color(0xFF1489C4));

    companion object {
        fun JobStatusColors.get(): Color = this.color
    }
}

enum class BuildResultStatusColors(val color: Color) {
    NONE(Color(0xFFFFC774)),
    SUCCESS(Color(0xFF02B884)),
    FAILURE(Color(0xFFba1b1b));

    companion object {
        fun BuildResultStatusColors.get(): Color = this.color
    }
}