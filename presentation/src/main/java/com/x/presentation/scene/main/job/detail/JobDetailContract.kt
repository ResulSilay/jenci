package com.x.presentation.scene.main.job.detail

import com.x.common.resource.Resource
import com.x.domain.model.JobModel
import com.x.domain.model.ResponseModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class JobDetailContract {

    data class State(
        var jobModel: Resource<JobModel>? = null,
        var buildModel: Resource<ResponseModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        data class GetJob(val jobUrl: String) : Event()
        data class Build(val jobUrl: String) : Event()
        data class BuildWithParameters(val jobUrl: String, val fieldMap: Map<String, String>) : Event()
    }
}