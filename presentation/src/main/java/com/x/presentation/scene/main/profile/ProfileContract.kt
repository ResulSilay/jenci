package com.x.presentation.scene.main.profile

import com.x.common.resource.Resource
import com.x.domain.model.ProfileModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class ProfileContract {

    data class State(
        var profileModel: Resource<ProfileModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        object GetProfile : Event()
    }
}