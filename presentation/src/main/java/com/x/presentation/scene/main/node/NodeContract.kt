package com.x.presentation.scene.main.node

import com.x.common.resource.Resource
import com.x.domain.model.NodeModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class NodeContract {

    data class State(
        var nodeModel: Resource<NodeModel>? = null
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetNodes : Event()
    }
}