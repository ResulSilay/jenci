package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.model.AccountModel
import com.x.domain.repository.api.UserRepository
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val userRepository: UserRepository,
    private val cachePreferences: CachePreferences,
) : UserUseCase {

    override fun getPeople() = flow {
        try {
            emit(Resource.Loading(true))
            val peopleModel = userRepository.getAll()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = peopleModel
                )
            )
        } catch (e: HttpException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }

    override fun getProfile() = flow {
        try {
            val username = cachePreferences.getModel(AccountModel::class)?.username.toString()
            emit(Resource.Loading(true))
            val profileModel = userRepository.get(username = username)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = profileModel
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }
}