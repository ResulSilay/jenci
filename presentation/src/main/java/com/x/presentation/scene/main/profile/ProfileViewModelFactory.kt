package com.x.presentation.scene.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.usecase.UserUseCase
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val userUseCase: UserUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userUseCase = userUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}