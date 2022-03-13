package com.x.presentation.scene.main.job.list

import com.x.common.resource.Resource
import com.x.domain.model.JobModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class JobListContract {

    data class State(
        var jobModel: Resource<JobModel>? = null,
        var isRefreshing: Boolean = false,
    ) : BaseState

    sealed class Event : BaseEvent {
        data class GetJobs(val jobName: String) : Event()
    }
}