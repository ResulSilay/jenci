package com.x.presentation.scene.main.job.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.JobUseCase
import javax.inject.Inject

class JobListViewModelFactory @Inject constructor(
    private val jobUseCase: JobUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JobListViewModel(jobUseCase = jobUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}