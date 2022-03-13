package com.x.presentation.scene.main.job.list

import com.x.presentation.scene.main.job.list.JobListContract.*
import com.x.presentation.scene.main.job.list.JobListContract.Event.*


fun JobListViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is GetJobs -> {
            getJobs(jobName = event.jobName)
        }
    }
}