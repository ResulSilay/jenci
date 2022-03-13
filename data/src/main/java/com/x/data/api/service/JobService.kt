package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.Query.jobBuildQuery
import com.x.data.api.Query.jobQuery
import com.x.data.api.model.response.JobBuildDetailResponse
import com.x.data.api.model.response.JobBuildResponse
import com.x.data.api.model.response.JobResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface JobService {

    @GET("{path}/$API/$JSON?tree=$jobQuery")
    suspend fun getJobs(
        @Path("path") path: String,
    ): JobResponse

    @POST("{path}/build")
    suspend fun build(
        @Path("path") path: String,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("{path}/buildWithParameters")
    suspend fun build(
        @Path("path") path: String,
        @FieldMap fieldMap: Map<String, String>,
    ): Response<ResponseBody>

    @GET("$API/$JSON?tree=$jobBuildQuery")
    suspend fun getBuilds(
        @Query("depth") depth: String = "0",
    ): JobBuildResponse

    @GET("{path}/{build_id}/$API/$JSON")
    suspend fun getBuild(
        @Path("path") path: String,
        @Path("build_id") buildId: String,
    ): JobBuildDetailResponse

    @GET("{path}/{build_id}/consoleText")
    suspend fun getConsoleLog(
        @Path("path") path: String,
        @Path("build_id") buildId: String,
    ): Response<String>
}