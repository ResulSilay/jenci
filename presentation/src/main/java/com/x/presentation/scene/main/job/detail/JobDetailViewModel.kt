package com.x.presentation.scene.main.job.detail

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.JobUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.job.detail.JobDetailContract.Event
import com.x.presentation.scene.main.job.detail.JobDetailContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val jobUseCase: JobUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getJob(jobUrl: String) {
        jobUseCase.getJob(jobUrl = jobUrl).onEach { result ->
            state.value = initState().copy(jobModel = result)
        }.launchIn(viewModelScope)
    }

    fun build(jobUrl: String) {
        jobUseCase.build(
            jobUrl = jobUrl
        ).onEach { result ->
            state.value = initState().copy(buildModel = result)
        }.launchIn(viewModelScope)
    }

    fun build(jobUrl: String, fieldMap: Map<String, String>) {
        jobUseCase.buildWithParameters(
            jobUrl = jobUrl,
            fieldMap = fieldMap
        ).onEach { result ->
            state.value = initState().copy(buildModel = result)
        }.launchIn(viewModelScope)
    }
}