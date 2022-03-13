package com.x.data.api.repository

import com.x.data.api.model.response.toModel
import com.x.data.api.service.NodeService
import com.x.domain.model.NodeDetailModel
import com.x.domain.model.NodeModel
import com.x.domain.repository.api.NodeRepository
import javax.inject.Inject

class NodeRepositoryImpl @Inject constructor(
    private val nodeService: NodeService,
) : NodeRepository {

    override suspend fun getAll(): NodeModel {
        return nodeService.getNodes().toModel()
    }

    override suspend fun getBy(name: String): NodeDetailModel {
        return nodeService.get(nodeName = name).toModel()
    }
}