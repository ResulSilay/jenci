package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.PeopleModel
import com.x.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun getPeople(): Flow<Resource<PeopleModel>>

    fun getProfile(): Flow<Resource<ProfileModel>>
}