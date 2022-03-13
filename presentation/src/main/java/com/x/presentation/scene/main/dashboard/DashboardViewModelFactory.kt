package com.x.presentation.scene.main.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.ActionUseCase
import com.x.domain.usecase.DashboardUseCase
import javax.inject.Inject

class DashboardViewModelFactory @Inject constructor(
    private val dashboardUseCase: DashboardUseCase,
    private val actionUseCase: ActionUseCase,
    private val cachePreferences: CachePreferences,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(
                dashboardUseCase = dashboardUseCase,
                actionUseCase = actionUseCase,
                cachePreferences = cachePreferences
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}