package com.x.presentation.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.x.data.api.Api
import com.x.data.api.interceptor.BasicAuthInterceptor
import com.x.data.api.interceptor.CookieJarInterceptor
import com.x.data.api.interceptor.HostSelectionInterceptor
import com.x.data.api.repository.*
import com.x.data.api.service.*
import com.x.data.util.MoshiHelper
import com.x.domain.repository.api.*
import com.x.domain.repository.cache.CachePreferences
import com.x.presentation.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OkHttpLoggerInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ChuckerLoggerInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class HostInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CookieInterceptor

    @OkHttpLoggerInterceptor
    @Provides
    @Singleton
    fun provideOkHttpLoggerInterceptor(): Interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @ChuckerLoggerInterceptor
    @Provides
    @Singleton
    fun provideChuckerLoggerInterceptor(@ApplicationContext context: Context): Interceptor = ChuckerInterceptor
        .Builder(context = context)
        .build()

    @HostInterceptor
    @Provides
    fun provideHostSelectionInterceptor(cachePreferences: CachePreferences): Interceptor =
        HostSelectionInterceptor(cachePreferences)

    @AuthInterceptor
    @Provides
    fun provideAuthInterceptor(cachePreferences: CachePreferences): Interceptor = BasicAuthInterceptor(cachePreferences)

    @CookieInterceptor
    @Provides
    @Singleton
    fun provideCookieInterceptor(): CookieJar = CookieJarInterceptor()

    @Provides
    fun provideApiClient(
        @OkHttpLoggerInterceptor httpLoggerInterceptor: Interceptor,
        @ChuckerLoggerInterceptor chuckerLoggerInterceptor: Interceptor,
        @HostInterceptor hostSelectionInterceptor: Interceptor,
        @AuthInterceptor basicAuthInterceptor: Interceptor,
        @CookieInterceptor cookieJarInterceptor: CookieJar,
    ): Retrofit {
        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggerInterceptor)
        }

        client
            .addInterceptor(chuckerLoggerInterceptor)
            .addInterceptor(hostSelectionInterceptor)
            .addInterceptor(basicAuthInterceptor)
            .cookieJar(cookieJarInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(client.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.getMoshiBuild()).asLenient())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideViewService(retrofit: Retrofit): ViewService {
        return retrofit.create(ViewService::class.java)
    }

    @Provides
    @Singleton
    fun provideJobService(retrofit: Retrofit): JobService {
        return retrofit.create(JobService::class.java)
    }

    @Provides
    @Singleton
    fun provideNodeService(retrofit: Retrofit): NodeService {
        return retrofit.create(NodeService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideActionService(retrofit: Retrofit): ActionService {
        return retrofit.create(ActionService::class.java)
    }

    @Provides
    @Singleton
    fun provideQueueService(retrofit: Retrofit): QueueService {
        return retrofit.create(QueueService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideViewRepository(viewService: ViewService): ViewRepository {
        return ViewRepositoryImpl(viewService = viewService)
    }

    @Provides
    @Singleton
    fun provideJobRepository(jobService: JobService): JobRepository {
        return JobRepositoryImpl(jobService = jobService)
    }

    @Provides
    @Singleton
    fun provideNodeRepository(nodeService: NodeService): NodeRepository {
        return NodeRepositoryImpl(nodeService = nodeService)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService = userService)
    }

    @Provides
    @Singleton
    fun provideActionRepository(actionService: ActionService): ActionRepository {
        return ActionRepositoryImpl(actionService = actionService)
    }

    @Provides
    @Singleton
    fun provideQueueRepository(queueService: QueueService): QueueRepository {
        return QueueRepositoryImpl(queueService = queueService)
    }
}