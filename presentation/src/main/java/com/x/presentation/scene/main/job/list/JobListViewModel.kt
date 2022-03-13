package com.x.presentation.scene.main.job.list

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.JobUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.job.list.JobListContract.Event
import com.x.presentation.scene.main.job.list.JobListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JobListViewModel @Inject constructor(
    private val jobUseCase: JobUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getJobs(jobName: String) {
        jobUseCase.getJob(jobUrl = jobName).onEach { result ->
            state.value = state.value.copy(jobModel = result)
        }.launchIn(viewModelScope)
    }
}