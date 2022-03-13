package com.x.presentation.util

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.x.presentation.R

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@Composable
fun Int.getString(): String {
    return LocalContext.current.getString(this)
}

@Composable
fun Int.getStringArray(): Array<out String> {
    return LocalContext.current.resources.getStringArray(this)
}

@Composable
fun Int.getStringList(): List<String> {
    return LocalContext.current.resources.getStringArray(this).toList()
}

@Composable
fun Int.getDrawable(): Drawable? {
    return ContextCompat.getDrawable(LocalContext.current, this)
}

@Composable
fun String?.toFieldEmptyString(): String {
    if (this == null || this == "null")
        return R.string.empty_field.getString()

    return this
}