package com.x.presentation.scene.main.node.detail

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.NodeUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.node.detail.NodeDetailContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NodeDetailViewModel @Inject constructor(
    private val nodeUseCase: NodeUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getNode(nodeName:String) {
        nodeUseCase.get(nodeName = nodeName).onEach { result ->
            state.value = initState().copy(nodeDetailModel = result)
        }.launchIn(viewModelScope)
    }
}