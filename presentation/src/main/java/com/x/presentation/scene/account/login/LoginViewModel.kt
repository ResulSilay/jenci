package com.x.presentation.scene.account.login

import androidx.lifecycle.viewModelScope
import com.x.domain.model.AccountModel
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.LoginUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.account.login.LoginContract.Event
import com.x.presentation.scene.account.login.LoginContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val cachePreferences: CachePreferences,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    init {
        publish(event = Event.GetAccountListEvent)
    }

    fun cacheManager() = cachePreferences

    fun onLogin(accountModel: AccountModel) {
        loginUseCase.login(accountModel).onEach { result ->
            state.value = initState().copy(accountModel = result)
        }.launchIn(viewModelScope)
    }

    fun getAccounts() {
        loginUseCase.getAccountList().onEach { result ->
            state.value = initState().copy(accountList = result)
        }.launchIn(viewModelScope)
    }

    fun accountRemove(accountId: Int) {
        loginUseCase.removeAccount(accountId = accountId).onEach { result ->
            state.value = initState().copy(accountRemove = result)
        }.launchIn(viewModelScope)
    }
}