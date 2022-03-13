package com.x.presentation.scene.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.SettingsUseCase
import javax.inject.Inject

class SettingsViewModelFactory @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val cachePreferences: CachePreferences,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                settingsUseCase = settingsUseCase,
                cachePreferences = cachePreferences
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}