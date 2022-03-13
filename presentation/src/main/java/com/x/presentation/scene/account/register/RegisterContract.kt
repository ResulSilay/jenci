package com.x.presentation.scene.account.register

import com.x.common.resource.Resource
import com.x.domain.model.AccountModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class RegisterContract {

    data class State(
        var isSave: Resource<Boolean>? = null,
        var accountModel: Resource<AccountModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        data class GetAccount(val accountId: Int) : Event()
        data class SaveAccount(val accountModel: AccountModel) : Event()
    }
}