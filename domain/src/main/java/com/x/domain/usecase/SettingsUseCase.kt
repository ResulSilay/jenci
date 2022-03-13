package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    fun logout(): Flow<Resource<ResponseModel>>
}