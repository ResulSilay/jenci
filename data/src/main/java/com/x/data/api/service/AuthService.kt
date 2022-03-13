package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.model.response.CrumbResponse
import com.x.data.api.model.response.TokenResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @GET("crumbIssuer/$API/$JSON")
    suspend fun getCrumb(
        @Header("Authorization") auth: String,
    ): CrumbResponse

    @POST("me/descriptorByName/jenkins.security.ApiTokenProperty/generateNewToken")
    suspend fun getToken(
        @Header("Authorization") auth: String,
        @Header("Jenkins-Crumb") crumb: String,
    ): TokenResponse

    @FormUrlEncoded
    @POST("me/descriptorByName/jenkins.security.ApiTokenProperty/revoke/$API/$JSON")
    suspend fun logout(
        @Field("Jenkins-Crumb") crumb: String,
        @Field("tokenUuid") tokenUuid: String,
    ): Response<ResponseBody>
}