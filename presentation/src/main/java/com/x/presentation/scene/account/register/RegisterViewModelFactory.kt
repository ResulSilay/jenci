package com.x.presentation.scene.account.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.AccountUseCase
import javax.inject.Inject

class RegisterViewModelFactory @Inject constructor(
    private val accountUseCase: AccountUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(accountUseCase = accountUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}