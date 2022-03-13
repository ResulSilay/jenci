package com.x.presentation.scene.main.node

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.NodeUseCase
import javax.inject.Inject

class NodeViewModelFactory @Inject constructor(
    private val nodeUseCase: NodeUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeUseCase::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NodeViewModel(nodeUseCase = nodeUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}