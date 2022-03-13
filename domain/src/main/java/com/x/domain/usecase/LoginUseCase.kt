package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.AccountModel
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(accountModel: AccountModel): Flow<Resource<AccountModel>>

    fun getAccountList(): Flow<Resource<List<AccountModel>>>

    fun removeAccount(accountId: Int): Flow<Resource<Unit>>
}