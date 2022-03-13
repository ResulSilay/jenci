package com.x.presentation.scene.main.profile

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.UserUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.profile.ProfileContract.Event
import com.x.presentation.scene.main.profile.ProfileContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel<Event, State>() {

    init {
        publish(event = Event.GetProfile)
    }

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getProfile() {
        userUseCase.getProfile().onEach { result ->
            state.value = initState().copy(profileModel = result)
        }.launchIn(viewModelScope)
    }
}