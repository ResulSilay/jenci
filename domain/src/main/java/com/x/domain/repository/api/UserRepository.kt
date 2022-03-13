package com.x.domain.repository.api

import com.x.domain.model.PeopleModel
import com.x.domain.model.ProfileModel

interface UserRepository {

    suspend fun getAll(): PeopleModel

    suspend fun get(username: String): ProfileModel
}