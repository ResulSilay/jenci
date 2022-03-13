package com.x.presentation.scene.account.login

import com.x.common.resource.Resource
import com.x.domain.model.AccountModel
import com.x.presentation.base.BaseEvent
import com.x.presentation.base.BaseState

class LoginContract {

    data class State(
        var accountList: Resource<List<AccountModel>>? = null,
        var accountRemove: Resource<Any>? = null,
        var accountModel: Resource<AccountModel>? = null,
    ) : BaseState

    sealed class Event : BaseEvent {
        class LoginEvent(val account: AccountModel) : Event()
        class AccountRemoveEvent(val accountId: Int) : Event()
        object GetAccountListEvent : Event()
    }
}