package com.x.presentation.scene.main.dashboard

import androidx.lifecycle.viewModelScope
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.ActionUseCase
import com.x.domain.usecase.DashboardUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.dashboard.DashboardContract.Event
import com.x.presentation.scene.main.dashboard.DashboardContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardUseCase: DashboardUseCase,
    private val actionUseCase: ActionUseCase,
    private val cachePreferences: CachePreferences,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun cacheManager() = cachePreferences

    fun getStats() {
        dashboardUseCase.getStats().onEach { result ->
            state.value = initState().copy(statsModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionRestart() {
        actionUseCase.restart().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionSafeRestart() {
        actionUseCase.safeRestart().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionShutdown() {
        actionUseCase.shutdown().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionSafeShutdown() {
        actionUseCase.safeShutdown().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionCancelQuietDown() {
        actionUseCase.cancelQuietDown().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }

    fun actionQuietDown() {
        actionUseCase.quietDown().onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }
}