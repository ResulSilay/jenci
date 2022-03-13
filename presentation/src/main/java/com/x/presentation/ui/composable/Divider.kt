package com.x.presentation.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.x.presentation.ui.theme.Theme

@Composable
fun DividerComposable(
    color: Color = Theme.colors.outline,
) {
    Divider(
        color = color,
        modifier = Modifier
            .fillMaxSize()
            .width(1.dp)
    )
}

@Composable
fun CustomDivider(
    color: Color = Theme.colors.outline,
) {
    Divider(color = color)
}