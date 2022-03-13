package com.x.data.api.repository

import com.x.data.api.model.response.toActionModel
import com.x.data.api.service.ActionService
import com.x.domain.model.ActionModel
import com.x.domain.repository.api.ActionRepository
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val actionService: ActionService,
) : ActionRepository {

    override suspend fun restart(): ActionModel {
        return actionService.restart().toActionModel()
    }

    override suspend fun safeRestart(): ActionModel {
        return actionService.safeRestart().toActionModel()
    }

    override suspend fun shutdown(): ActionModel {
        return actionService.shutdown().toActionModel()
    }

    override suspend fun safeShutdown(): ActionModel {
        return actionService.safeshutdown().toActionModel()
    }

    override suspend fun cancelQuietDown(): ActionModel {
        return actionService.cancelQuietDown().toActionModel()
    }

    override suspend fun quietDown(): ActionModel {
        return actionService.quietDown().toActionModel()
    }
}