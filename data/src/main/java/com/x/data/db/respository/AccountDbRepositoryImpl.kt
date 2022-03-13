package com.x.data.db.respository

import com.x.data.db.dao.AccountDao
import com.x.data.db.entity.Account
import com.x.domain.model.AccountModel
import com.x.domain.repository.db.AccountDbRepository
import javax.inject.Inject

class AccountDbRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao,
) : AccountDbRepository {

    override suspend fun getAll(): List<AccountModel>? {
        return accountDao.all()?.map {
            AccountModel(
                accountId = it.accountId,
                name = it.name,
                host = it.host,
                username = it.username,
                password = it.password,
            )
        }
    }

    override suspend fun get(accountId: Int): AccountModel? {
        return accountDao.get(accountId = accountId)?.let {
            AccountModel(
                accountId = it.accountId,
                name = it.name,
                host = it.host,
                username = it.username,
                password = it.password,
            )
        }
    }

    override suspend fun save(accountModel: AccountModel) {
        val account = accountDao.get(accountId = accountModel.accountId)
        val accountItem = Account(
            accountId = accountModel.accountId,
            name = accountModel.name,
            host = accountModel.host,
            username = accountModel.username,
            password = accountModel.password
        )
        return if (account != null) {
            accountDao.update(account = accountItem)
        } else {
            accountDao.insert(account = accountItem)
        }
    }

    override suspend fun removeAt(accountId: Long) {
        return accountDao.removeAt(accountId = accountId)
    }
}