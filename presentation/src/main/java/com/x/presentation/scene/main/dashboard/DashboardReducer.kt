package com.x.presentation.scene.main.dashboard

import com.x.presentation.scene.main.dashboard.DashboardContract.Event
import com.x.presentation.scene.main.dashboard.DashboardContract.Event.*

fun DashboardViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetStats -> {
            getStats()
        }
        is ActionRestart -> {
            actionRestart()
        }
        is ActionSafeRestart -> {
            actionSafeRestart()
        }
        is ActionCancelQuietDown -> {
            actionCancelQuietDown()
        }
        is ActionQuietDown -> {
            actionQuietDown()
        }
        is ActionSafeShutdown -> {
            actionSafeShutdown()
        }
        is ActionShutdown -> {
            actionShutdown()
        }
    }
}