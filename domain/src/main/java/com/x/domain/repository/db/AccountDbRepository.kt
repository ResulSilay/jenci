package com.x.domain.repository.db

import com.x.domain.model.AccountModel

interface AccountDbRepository {

    suspend fun getAll(): List<AccountModel>?

    suspend fun get(accountId: Int): AccountModel?

    suspend fun save(accountModel: AccountModel)

    suspend fun removeAt(accountId: Long)
}