package com.x.data.api.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST

interface ActionService {

    @POST("restart")
    suspend fun restart(): Response<ResponseBody>

    @POST("safeRestart")
    suspend fun safeRestart(): Response<ResponseBody>

    @POST("shutdown")
    suspend fun shutdown(): Response<ResponseBody>

    @POST("safeshutdown")
    suspend fun safeshutdown(): Response<ResponseBody>

    @POST("quietDown")
    suspend fun quietDown(): Response<ResponseBody>

    @POST("cancelQuietDown")
    suspend fun cancelQuietDown(): Response<ResponseBody>
}