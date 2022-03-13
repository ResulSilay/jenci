package com.x.data.db.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accountId")
    val accountId: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "host")
    val host: String?,
    @ColumnInfo(name = "username")
    val username: String?,
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "isSSL")
    val isSSL: Boolean? = false,
    @ColumnInfo(name = "isBiometric")
    val isBiometric: Boolean? = false,
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long? = System.currentTimeMillis(),
    @ColumnInfo(name = "createdAt")
    val createdAt: Long? = System.currentTimeMillis(),
)