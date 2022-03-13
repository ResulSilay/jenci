package com.x.presentation.scene.main.job.build.detail

import com.x.common.resource.Resource
import com.x.domain.model.JobBuildDetailModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class JobBuildDetailContract {

    data class State(
        var jobBuildDetailModel: Resource<JobBuildDetailModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        data class GetBuild(val jobUrl: String, val buildId: String) : Event()
    }
}