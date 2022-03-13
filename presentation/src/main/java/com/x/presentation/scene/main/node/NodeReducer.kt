package com.x.presentation.scene.main.node

import com.x.presentation.scene.main.node.NodeContract.*
import com.x.presentation.scene.main.node.NodeContract.Event.*

fun NodeViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetNodes -> {
            getViews()
        }
    }
}