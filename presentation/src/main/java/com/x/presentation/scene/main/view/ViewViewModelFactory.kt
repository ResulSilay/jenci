package com.x.presentation.scene.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.ViewUseCase
import javax.inject.Inject

class ViewViewModelFactory @Inject constructor(
    private val viewUseCase: ViewUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewViewModel(viewUseCase = viewUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}