package com.x.presentation.scene.main.people

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.UserUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.people.PeopleContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
) : BaseViewModel<Event, State>() {

    init {
        publish(event = Event.GetPeople)
    }

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getPeople() {
        userUseCase.getPeople().onEach { result ->
            state.value = initState().copy(peopleModel = result)
        }.launchIn(viewModelScope)
    }
}