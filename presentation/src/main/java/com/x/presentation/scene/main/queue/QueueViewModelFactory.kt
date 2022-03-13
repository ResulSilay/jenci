package com.x.presentation.scene.main.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.QueueUseCase
import javax.inject.Inject

class QueueViewModelFactory @Inject constructor(
    private val queueUseCase: QueueUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QueueViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QueueViewModel(queueUseCase = queueUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}