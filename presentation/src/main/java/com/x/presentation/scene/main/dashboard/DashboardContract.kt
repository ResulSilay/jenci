package com.x.presentation.scene.main.dashboard

import com.x.common.resource.Resource
import com.x.domain.model.ActionModel
import com.x.domain.model.StatsModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class DashboardContract {

    data class State(
        var statsModel: Resource<StatsModel>? = null,
        var actionModel: Resource<ActionModel>? = null,
        var isRefreshing: Boolean = false,
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetStats : Event()
        object ActionRestart : Event()
        object ActionSafeRestart : Event()
        object ActionShutdown : Event()
        object ActionSafeShutdown : Event()
        object ActionCancelQuietDown : Event()
        object ActionQuietDown : Event()
    }
}