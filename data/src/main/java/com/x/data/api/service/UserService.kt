package com.x.data.api.service

import com.x.data.api.Api.API
import com.x.data.api.Api.JSON
import com.x.data.api.model.response.PeopleResponse
import com.x.data.api.model.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("asynchPeople/$API/$JSON")
    suspend fun getPeoples(): PeopleResponse

    @GET("user/{user_name}/$API/$JSON")
    suspend fun getUser(
        @Path("user_name") username: String,
    ): ProfileResponse
}