package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.ActionModel
import com.x.domain.model.QueueModel
import kotlinx.coroutines.flow.Flow

interface QueueUseCase {

    fun getAllQueue(): Flow<Resource<QueueModel>>

    fun cancelQueue(queueId: String): Flow<Resource<ActionModel>>
}