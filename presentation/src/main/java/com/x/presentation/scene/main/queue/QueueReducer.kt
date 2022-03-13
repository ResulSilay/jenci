package com.x.presentation.scene.main.queue

import com.x.presentation.scene.main.queue.QueueContract.Event
import com.x.presentation.scene.main.queue.QueueContract.Event.*

fun QueueViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetAllQueue -> {
            getQueues()
        }
        is CancelQueue -> {
            cancelQueue(queueId = event.queueId)
        }
    }
}