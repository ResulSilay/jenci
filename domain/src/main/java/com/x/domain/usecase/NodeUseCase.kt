package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.NodeDetailModel
import com.x.domain.model.NodeModel
import kotlinx.coroutines.flow.Flow

interface NodeUseCase {

    fun getAllNode(): Flow<Resource<NodeModel>>

    fun get(nodeName: String): Flow<Resource<NodeDetailModel>>
}