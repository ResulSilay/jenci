package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.ActionModel
import kotlinx.coroutines.flow.Flow

interface ActionUseCase {

    fun restart(): Flow<Resource<ActionModel>>

    fun safeRestart(): Flow<Resource<ActionModel>>

    fun shutdown(): Flow<Resource<ActionModel>>

    fun safeShutdown(): Flow<Resource<ActionModel>>

    fun cancelQuietDown(): Flow<Resource<ActionModel>>

    fun quietDown(): Flow<Resource<ActionModel>>
}