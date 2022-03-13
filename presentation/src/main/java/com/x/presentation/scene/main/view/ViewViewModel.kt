package com.x.presentation.scene.main.view

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.ViewUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.view.ViewContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ViewViewModel @Inject constructor(
    private val viewUseCase: ViewUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getViews() {
        viewUseCase.getAllView().onEach { result ->
            state.value = state.value.copy(viewModel = result)
        }.launchIn(viewModelScope)
    }

    fun getViewJobs(viewName: String) {
        viewUseCase.getViewJobs(viewName = viewName).onEach { result ->
            state.value = state.value.copy(jobViewListModel = result)
        }.launchIn(viewModelScope)
    }
}