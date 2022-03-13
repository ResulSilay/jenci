package com.x.presentation.di

import com.x.common.logger.Logger
import com.x.common.logger.LoggerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return LoggerImpl()
    }
}