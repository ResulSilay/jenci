package com.x.presentation.scene.account.register

import androidx.lifecycle.viewModelScope
import com.x.domain.model.AccountModel
import com.x.domain.usecase.AccountUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.account.register.RegisterContract.Event
import com.x.presentation.scene.account.register.RegisterContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun save(accountModel: AccountModel) {
        accountUseCase.save(accountModel = accountModel).onEach { result ->
            state.value = initState().copy(isSave = result)
        }.launchIn(viewModelScope)
    }

    fun getAccount(accountId: Int) {
        accountUseCase.getAccount(accountId = accountId).onEach { result ->
            state.value = initState().copy(accountModel = result)
        }.launchIn(viewModelScope)
    }
}