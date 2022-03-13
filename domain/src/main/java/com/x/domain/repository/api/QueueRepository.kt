package com.x.domain.repository.api

import com.x.domain.model.ActionModel
import com.x.domain.model.QueueModel

interface QueueRepository {

    suspend fun getAll(): QueueModel

    suspend fun cancelQueue(queueId: String): ActionModel
}