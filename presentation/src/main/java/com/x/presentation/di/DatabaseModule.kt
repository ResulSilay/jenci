package com.x.presentation.di

import android.content.Context
import com.x.data.db.AppDatabase
import com.x.data.db.dao.AccountDao
import com.x.data.db.respository.AccountDbRepositoryImpl
import com.x.domain.repository.db.AccountDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context = context)
    }

    @Provides
    @Singleton
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Provides
    @Singleton
    fun provideAccountDbRepository(accountDao: AccountDao): AccountDbRepository {
        return AccountDbRepositoryImpl(accountDao = accountDao)
    }
}