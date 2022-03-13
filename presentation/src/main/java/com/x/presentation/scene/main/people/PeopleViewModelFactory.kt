package com.x.presentation.scene.main.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.UserUseCase
import javax.inject.Inject

class PeopleViewModelFactory @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PeopleViewModel(userUseCase = userUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}