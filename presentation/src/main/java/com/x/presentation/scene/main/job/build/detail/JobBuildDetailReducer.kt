package com.x.presentation.scene.main.job.build.detail

import com.x.presentation.scene.main.job.build.detail.JobBuildDetailContract.*
import com.x.presentation.scene.main.job.build.detail.JobBuildDetailContract.Event.*

fun JobBuildDetailViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetBuild -> {
            getBuild(jobUrl = event.jobUrl, buildId = event.buildId)
        }
    }
}