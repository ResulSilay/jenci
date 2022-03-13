package com.x.presentation.scene.main.job.detail

import com.x.presentation.scene.main.job.detail.JobDetailContract.Event
import com.x.presentation.scene.main.job.detail.JobDetailContract.Event.*

fun JobDetailViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetJob -> {
            getJob(jobUrl = event.jobUrl)
        }
        is Build -> {
            build(jobUrl = event.jobUrl)
        }
        is BuildWithParameters -> {
            build(jobUrl = event.jobUrl, fieldMap = event.fieldMap)
        }
    }
}