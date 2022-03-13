package com.x.presentation.scene.main.people

import com.x.common.resource.Resource
import com.x.domain.model.PeopleModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class PeopleContract {

    data class State(
        var peopleModel: Resource<PeopleModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetPeople : Event()
    }
}