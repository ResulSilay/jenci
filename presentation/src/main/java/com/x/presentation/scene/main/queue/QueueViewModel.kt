package com.x.presentation.scene.main.queue

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.QueueUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.queue.QueueContract.Event
import com.x.presentation.scene.main.queue.QueueContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class QueueViewModel @Inject constructor(
    private val queueUseCase: QueueUseCase,
) : BaseViewModel<Event, State>() {

    init {
        publish(event = Event.GetAllQueue)
    }

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getQueues() {
        queueUseCase.getAllQueue().onEach { result ->
            state.value = initState().copy(queueModel = result)
        }.launchIn(viewModelScope)
    }

    fun cancelQueue(queueId: String) {
        queueUseCase.cancelQueue(queueId = queueId).onEach { result ->
            state.value = initState().copy(actionModel = result)
        }.launchIn(viewModelScope)
    }
}