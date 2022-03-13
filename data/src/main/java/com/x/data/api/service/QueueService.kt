package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.model.response.QueueResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QueueService {

    @GET("queue/$API/$JSON")
    suspend fun getQueues(): QueueResponse

    @POST("queue/cancelItem")
    suspend fun queueCancel(
        @Query("id") queueId: String,
    ): Response<ResponseBody>
}