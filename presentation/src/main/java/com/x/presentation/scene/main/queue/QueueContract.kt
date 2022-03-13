package com.x.presentation.scene.main.queue

import com.x.common.resource.Resource
import com.x.domain.model.ActionModel
import com.x.domain.model.QueueModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class QueueContract {

    data class State(
        var queueModel: Resource<QueueModel>? = null,
        var actionModel: Resource<ActionModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetAllQueue : Event()
        data class CancelQueue(val queueId: String) : Event()
    }
}