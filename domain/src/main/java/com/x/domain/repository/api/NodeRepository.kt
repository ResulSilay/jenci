package com.x.domain.repository.api

import com.x.domain.model.NodeDetailModel
import com.x.domain.model.NodeModel

interface NodeRepository {

    suspend fun getAll(): NodeModel

    suspend fun getBy(name: String): NodeDetailModel
}