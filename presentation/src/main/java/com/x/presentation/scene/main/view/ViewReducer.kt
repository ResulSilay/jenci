package com.x.presentation.scene.main.view

import com.x.presentation.scene.main.view.ViewContract.*
import com.x.presentation.scene.main.view.ViewContract.Event.*

fun ViewViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetViews -> {
            getViews()
        }
        is GetViewJobs -> {
            getViewJobs(viewName = event.viewName)
        }
    }
}