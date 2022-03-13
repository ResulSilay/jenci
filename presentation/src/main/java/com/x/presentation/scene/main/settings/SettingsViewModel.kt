package com.x.presentation.scene.main.settings

import androidx.lifecycle.viewModelScope
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.SettingsUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.settings.SettingsContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val cachePreferences: CachePreferences,
) : BaseViewModel<Event, State>() {

    fun cacheManager() = cachePreferences

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun onLogout() {
        settingsUseCase.logout().onEach { result ->
            state.value = initState().copy(responseModel = result)
        }.launchIn(viewModelScope)
    }
}