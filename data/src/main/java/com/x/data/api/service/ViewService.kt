package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.model.response.ViewJobResponse
import com.x.data.api.model.response.ViewResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ViewService {

    @GET("$API/$JSON")
    suspend fun getViews(): ViewResponse

    @GET("view/{view_name}/$API/$JSON")
    suspend fun getJobs(
        @Path("view_name") viewName: String,
    ): ViewJobResponse
}