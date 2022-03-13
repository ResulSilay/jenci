package com.x.presentation.scene.main.node

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.NodeUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.node.NodeContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    private val nodeUseCase: NodeUseCase,
) : BaseViewModel<Event, State>() {

    init {
        publish(event = Event.GetNodes)
    }

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getViews() {
        nodeUseCase.getAllNode().onEach { result ->
            state.value = initState().copy(nodeModel = result)
        }.launchIn(viewModelScope)
    }
}