package com.x.data.api.repository

import com.x.data.api.model.response.toModel
import com.x.data.api.service.UserService
import com.x.domain.model.PeopleModel
import com.x.domain.model.ProfileModel
import com.x.domain.repository.api.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : UserRepository {

    override suspend fun getAll(): PeopleModel {
        return userService.getPeoples().toModel()
    }

    override suspend fun get(username: String): ProfileModel {
        return userService.getUser(username = username).toModel()
    }
}