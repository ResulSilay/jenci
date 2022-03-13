package com.x.data.api.repository

import com.x.data.api.model.response.toActionModel
import com.x.data.api.model.response.toModel
import com.x.data.api.service.QueueService
import com.x.domain.model.ActionModel
import com.x.domain.model.QueueModel
import com.x.domain.repository.api.QueueRepository
import javax.inject.Inject

class QueueRepositoryImpl @Inject constructor(
    private val queueService: QueueService,
) : QueueRepository {

    override suspend fun getAll(): QueueModel {
        return queueService.getQueues().toModel()
    }

    override suspend fun cancelQueue(queueId: String): ActionModel {
        return queueService.queueCancel(queueId = queueId).toActionModel()
    }
}