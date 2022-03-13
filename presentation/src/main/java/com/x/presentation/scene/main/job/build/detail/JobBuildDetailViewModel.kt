package com.x.presentation.scene.main.job.build.detail

import androidx.lifecycle.viewModelScope
import com.x.domain.usecase.JobUseCase
import com.x.presentation.base.BaseViewModel
import com.x.presentation.scene.main.job.build.detail.JobBuildDetailContract.Event
import com.x.presentation.scene.main.job.build.detail.JobBuildDetailContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JobBuildDetailViewModel @Inject constructor(
    private val jobUseCase: JobUseCase,
) : BaseViewModel<Event, State>() {

    override fun initState(): State = State()

    override fun handleEvent(event: Event) {
        reduceEvent(event = event)
    }

    fun getBuild(jobUrl: String, buildId: String) {
        jobUseCase.getBuild(
            jobUrl = jobUrl,
            buildId = buildId
        ).onEach { result ->
            state.value = initState().copy(
                jobBuildDetailModel = result
            )
        }.launchIn(viewModelScope)
    }
}