package com.x.presentation.scene.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val cachePreferences: CachePreferences,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginUseCase, cachePreferences) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}