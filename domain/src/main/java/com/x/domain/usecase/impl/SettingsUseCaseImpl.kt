package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.model.AccountModel
import com.x.domain.repository.api.AuthRepository
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val authRepository: AuthRepository,
    private val cachePreferences: CachePreferences,
) : SettingsUseCase {

    override fun logout() = flow {
        try {
            emit(Resource.Loading(true))
            val accountModel = cachePreferences.getModel(AccountModel::class)
            val responseModel = authRepository.logout(
                crumb = accountModel?.authCrumb?.crumb.toString(),
                tokenUuid = accountModel?.authToken?.uuid.toString()
            )
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = responseModel
                )
            )
        } catch (e: IOException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
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
}