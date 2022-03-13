package com.x.presentation.ui.composable

import android.os.CountDownTimer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.x.presentation.ui.theme.Theme

@Composable
fun CircularLoading(
    color: Color = Theme.colors.primary,
    backgroundColor: Color = Theme.colors.background,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize(),
    ) {
        CircularProgressIndicator(
            color = color
        )
    }
}

@Composable
fun CircularCountDownLoading(
    color: Color = Theme.colors.primary,
    onFinish: () -> Unit,
) {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    val countDownTimer = object : CountDownTimer(1000L, 100L) {
        override fun onTick(millisUntilFinished: Long) {
            progress = (((1000.0 - millisUntilFinished) / 1000.0)).toFloat()
        }

        override fun onFinish() {
            progress = 1F
            cancel()
            onFinish()
        }
    }

    DisposableEffect(Unit) {
        countDownTimer.start()
        onDispose {}
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            all = Theme.dimens.space.medium
        )
    ) {
        CircularProgressIndicator(
            color = color,
            progress = animatedProgress
        )
    }
}