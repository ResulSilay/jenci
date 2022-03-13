package com.x.domain.repository.api

import com.x.domain.model.AuthCrumbModel
import com.x.domain.model.AuthTokenModel
import com.x.domain.model.ResponseModel

interface AuthRepository {

    suspend fun getCrumb(auth: String): AuthCrumbModel

    suspend fun getToken(auth: String, crumb: String): AuthTokenModel

    suspend fun logout(crumb: String, tokenUuid: String): ResponseModel
}