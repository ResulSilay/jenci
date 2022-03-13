package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.model.response.NodeDetailResponse
import com.x.data.api.model.response.NodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NodeService {

    @GET("computer/$API/$JSON")
    suspend fun getNodes(): NodeResponse

    @GET("computer/{node_name}/$API/$JSON")
    suspend fun get(
        @Path("node_name") nodeName: String,
    ): NodeDetailResponse
}