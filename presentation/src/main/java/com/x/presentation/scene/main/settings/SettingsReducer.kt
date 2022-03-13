package com.x.presentation.scene.main.settings

import com.x.presentation.scene.main.settings.SettingsContract.*
import com.x.presentation.scene.main.settings.SettingsContract.Event.*

fun SettingsViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is OnLogout -> {
            onLogout()
        }
    }
}