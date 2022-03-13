package com.x.presentation.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Dimensions(
    val none: None,
    val text: Text,
    val space: Space,
    val elevation: Elevation,
    val vector: Vector,
)

class None(
    val default: Dp,
)

class Text(
    val small: TextUnit,
    val normal: TextUnit,
    val medium: TextUnit,
    val large: TextUnit,
    val xlarge: TextUnit,
)

class Space(
    val min: Dp,
    val xMin: Dp,
    val small: Dp,
    val smallExtra: Dp,
    val medium: Dp,
    val large: Dp,
    val largeExtra: Dp,
    val xlarge: Dp,
    val xxlarge: Dp,
)

class Elevation(
    val none: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
)

class Vector(
    val small: Dp,
    val smallExtra: Dp,
    val medium: Dp,
    val large: Dp,
    val largeExtra: Dp,
    val xlarge: Dp,
)

val DefaultDimensions = Dimensions(
    none = None(
        default = 0.dp
    ),
    text = Text(
        small = 12.sp,
        normal = 14.sp,
        medium = 16.sp,
        large = 18.sp,
        xlarge = 22.sp
    ),
    space = Space(
        min = 4.dp,
        xMin = 6.dp,
        small = 8.dp,
        smallExtra = 10.dp,
        medium = 16.dp,
        large = 24.dp,
        largeExtra = 32.dp,
        xlarge = 40.dp,
        xxlarge = 48.dp
    ),
    elevation = Elevation(
        none = 0.dp,
        small = 2.dp,
        medium = 4.dp,
        large = 8.dp
    ),
    vector = Vector(
        small = 18.dp,
        smallExtra = 20.dp,
        medium = 24.dp,
        large = 48.dp,
        largeExtra = 64.dp,
        xlarge = 94.dp
    ),
)