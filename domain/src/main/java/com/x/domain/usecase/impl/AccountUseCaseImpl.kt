package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.model.AccountModel
import com.x.domain.repository.db.AccountDbRepository
import com.x.domain.usecase.AccountUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val accountDbRepository: AccountDbRepository,
) : AccountUseCase {

    override fun save(accountModel: AccountModel) = flow {
        try {
            accountDbRepository.save(
                accountModel = AccountModel(
                    accountId = accountModel.accountId,
                    name = accountModel.name,
                    host = accountModel.host,
                    username = accountModel.username,
                    password = accountModel.password
                )
            )
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = true
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

    override fun getAccount(accountId: Int) = flow {
        try {
            val accountModel = accountDbRepository.get(accountId = accountId)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = accountModel
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