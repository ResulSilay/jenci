package com.x.domain.repository.api

import com.x.domain.model.ActionModel

interface ActionRepository {

    suspend fun restart(): ActionModel

    suspend fun safeRestart(): ActionModel

    suspend fun shutdown(): ActionModel

    suspend fun safeShutdown(): ActionModel

    suspend fun cancelQuietDown(): ActionModel

    suspend fun quietDown(): ActionModel
}