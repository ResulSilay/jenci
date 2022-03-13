package com.x.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

class Shape(
    val none: RoundedCornerShape,
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
    val xLarge: RoundedCornerShape,
    val xLargeExtra: RoundedCornerShape,
)

val Shapes = Shape(
    none = RoundedCornerShape(0.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    xLarge = RoundedCornerShape(32.dp),
    xLargeExtra = RoundedCornerShape(36.dp),
)