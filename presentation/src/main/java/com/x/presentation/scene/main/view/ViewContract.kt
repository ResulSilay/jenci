package com.x.presentation.scene.main.view

import com.x.common.resource.Resource
import com.x.domain.model.JobViewListModel
import com.x.domain.model.ViewModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class ViewContract {

    data class State(
        var viewModel: Resource<ViewModel>? = null,
        var jobViewListModel: Resource<JobViewListModel>? = null,
        var isRefreshing: Boolean = false,
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetViews : Event()
        class GetViewJobs(val viewName: String) : Event()
    }
}