package com.x.data.api.repository

import com.x.data.api.service.AuthService
import com.x.data.api.model.response.toModel
import com.x.domain.model.AuthCrumbModel
import com.x.domain.model.AuthTokenModel
import com.x.domain.model.ResponseModel
import com.x.domain.repository.api.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun getCrumb(auth: String): AuthCrumbModel {
        return authService.getCrumb(auth).toModel()
    }

    override suspend fun getToken(auth: String, crumb: String): AuthTokenModel {
        return authService.getToken(auth, crumb).toModel()
    }

    override suspend fun logout(crumb: String, tokenUuid: String): ResponseModel {
        return authService.logout(crumb, tokenUuid).toModel()
    }
}