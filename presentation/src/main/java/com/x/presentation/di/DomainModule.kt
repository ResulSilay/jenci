package com.x.presentation.di

import com.x.common.logger.Logger
import com.x.domain.repository.api.*
import com.x.domain.repository.cache.CachePreferences
import com.x.domain.repository.db.AccountDbRepository
import com.x.domain.usecase.*
import com.x.domain.usecase.impl.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideAccountUseCase(
        logger: Logger,
        accountDbRepository: AccountDbRepository,
    ): AccountUseCase {
        return AccountUseCaseImpl(
            logger = logger,
            accountDbRepository = accountDbRepository
        )
    }

    @Provides
    @Singleton
    fun provideActionUseCase(
        logger: Logger,
        actionRepository: ActionRepository,
    ): ActionUseCase {
        return ActionUseCaseImpl(
            logger = logger,
            actionRepository = actionRepository
        )
    }

    @Provides
    @Singleton
    fun provideDashboardUseCase(
        logger: Logger,
        nodeRepository: NodeRepository,
        viewRepository: ViewRepository,
        jobRepository: JobRepository,
        queueRepository: QueueRepository,
    ): DashboardUseCase {
        return DashboardUseCaseImpl(
            logger = logger,
            nodeRepository = nodeRepository,
            viewRepository = viewRepository,
            jobRepository = jobRepository,
            queueRepository = queueRepository,
        )
    }

    @Provides
    @Singleton
    fun provideJobUseCase(
        logger: Logger,
        jobRepository: JobRepository,
    ): JobUseCase {
        return JobUseCaseImpl(
            logger = logger,
            jobRepository = jobRepository
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        logger: Logger,
        authRepository: AuthRepository,
        accountDbRepository: AccountDbRepository,
    ): LoginUseCase {
        return LoginUseCaseImpl(
            logger = logger,
            authRepository = authRepository,
            accountDbRepository = accountDbRepository
        )
    }

    @Provides
    @Singleton
    fun provideNodeUseCase(
        logger: Logger,
        nodeRepository: NodeRepository,
    ): NodeUseCase {
        return NodeUseCaseImpl(
            logger = logger,
            nodeRepository = nodeRepository
        )
    }

    @Provides
    @Singleton
    fun provideQueueUseCase(
        logger: Logger,
        queueRepository: QueueRepository,
    ): QueueUseCase {
        return QueueUseCaseImpl(
            logger = logger,
            queueRepository = queueRepository
        )
    }

    @Provides
    @Singleton
    fun provideSettingsUseCase(
        logger: Logger,
        authRepository: AuthRepository,
        cachePreferences: CachePreferences,
    ): SettingsUseCase {
        return SettingsUseCaseImpl(
            logger = logger,
            authRepository = authRepository,
            cachePreferences = cachePreferences
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCase(
        logger: Logger,
        userRepository: UserRepository,
        cachePreferences: CachePreferences,
    ): UserUseCase {
        return UserUseCaseImpl(
            logger = logger,
            userRepository = userRepository,
            cachePreferences = cachePreferences
        )
    }

    @Provides
    @Singleton
    fun provideViewUseCase(
        logger: Logger,
        viewRepository: ViewRepository,
    ): ViewUseCase {
        return ViewUseCaseImpl(
            logger = logger,
            viewRepository = viewRepository
        )
    }
}