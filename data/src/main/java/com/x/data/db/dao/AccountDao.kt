package com.x.data.db.dao

import androidx.room.*
import com.x.data.db.entity.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("DELETE FROM account WHERE accountId = :accountId")
    suspend fun removeAt(accountId: Long?)

    @Query("SELECT * FROM account ORDER BY accountId DESC")
    suspend fun all(): List<Account>?

    @Query("SELECT * FROM account WHERE accountId = :accountId")
    suspend fun get(accountId: Int?): Account?
}