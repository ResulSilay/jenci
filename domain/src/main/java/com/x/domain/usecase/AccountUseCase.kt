package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.AccountModel
import kotlinx.coroutines.flow.Flow

interface AccountUseCase {

    fun save(accountModel: AccountModel): Flow<Resource<Boolean>>

    fun getAccount(accountId: Int): Flow<Resource<AccountModel>>
}