package com.x.presentation.scene.main.node.detail

import com.x.common.resource.Resource
import com.x.domain.model.NodeDetailModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class NodeDetailContract {

    data class State(
        var nodeDetailModel: Resource<NodeDetailModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        data class GetNode(val nodeName: String) : Event()
    }
}