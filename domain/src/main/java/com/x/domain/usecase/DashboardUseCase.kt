package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.StatsModel
import kotlinx.coroutines.flow.Flow

interface DashboardUseCase {

    fun getStats(): Flow<Resource<StatsModel>>
}