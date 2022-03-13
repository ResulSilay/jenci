package com.x.domain.usecase.impl

import com.x.common.extension.toBasicAuth
import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.model.AccountModel
import com.x.domain.repository.api.AuthRepository
import com.x.domain.repository.db.AccountDbRepository
import com.x.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val authRepository: AuthRepository,
    private val accountDbRepository: AccountDbRepository,
) : LoginUseCase {

    override fun login(accountModel: AccountModel) = flow {
        try {
            emit(Resource.Loading(true))
            val basicAuth = toBasicAuth(accountModel.username.toString(), accountModel.password.toString())
            val authCrumbModel = authRepository.getCrumb(basicAuth)
            val authTokenModel = authRepository.getToken(basicAuth, authCrumbModel.crumb.toString())

            val accountModelResult = AccountModel().apply {
                authToken = authTokenModel
                authCrumb = authCrumbModel
            }
            
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = accountModelResult
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

    override fun getAccountList() = flow {
        try {
            emit(Resource.Loading(true))
            val response = accountDbRepository.getAll()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = response
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

    override fun removeAccount(accountId: Int) = flow {
        try {
            emit(Resource.Loading(true))
            val response = accountDbRepository.removeAt(accountId = accountId.toLong())
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = response
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