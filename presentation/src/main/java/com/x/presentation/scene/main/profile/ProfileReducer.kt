package com.x.presentation.scene.main.profile

import com.x.presentation.scene.main.profile.ProfileContract.*
import com.x.presentation.scene.main.profile.ProfileContract.Event.*

fun ProfileViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetProfile -> {
            getProfile()
        }
    }
}