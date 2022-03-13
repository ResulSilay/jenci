package com.x.presentation.scene.main.settings

import com.x.common.resource.Resource
import com.x.domain.model.ResponseModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class SettingsContract {

    data class State(
        var responseModel: Resource<ResponseModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        object OnLogout : Event()
    }
}