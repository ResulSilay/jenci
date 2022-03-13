package com.x.presentation.scene.main.node.detail

import com.x.presentation.scene.main.node.detail.NodeDetailContract.*
import com.x.presentation.scene.main.node.detail.NodeDetailContract.Event.*

fun NodeDetailViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetNode -> {
            getNode(nodeName = event.nodeName)
        }
    }
}