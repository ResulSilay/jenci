package com.x.presentation.scene.main.job.build.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.JobUseCase
import javax.inject.Inject

class JobBuildDetailViewModelFactory @Inject constructor(
    private val jobUseCase: JobUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobBuildDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobBuildDetailViewModel(jobUseCase = jobUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}