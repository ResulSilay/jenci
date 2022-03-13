package com.x.presentation.util

import androidx.compose.runtime.MutableState

fun validationParams(
    vararg errorParams: MutableState<Boolean>,
): Boolean {
    errorParams.forEachIndexed { _, mutableState ->
        if (mutableState.value) {
            return false
        }
    }
    return true
}