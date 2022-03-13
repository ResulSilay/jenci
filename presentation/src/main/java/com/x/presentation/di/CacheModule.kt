package com.x.presentation.di

import android.content.Context
import com.x.data.cache.CachePreferencesImpl
import com.x.domain.repository.cache.CachePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideCachePreferences(@ApplicationContext context: Context): CachePreferences {
        return CachePreferencesImpl(context = context)
    }
}