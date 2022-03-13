package com.x.presentation.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController

object Navigation {

    @Composable
    fun StatusBarColor(color: Color, systemUiController: SystemUiController) {
        SideEffect {
            systemUiController.setStatusBarColor(color, false)
        }
    }
}