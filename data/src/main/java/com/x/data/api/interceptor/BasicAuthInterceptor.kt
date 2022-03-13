package com.x.data.api.interceptor

import com.x.domain.model.AccountModel
import com.x.domain.repository.cache.CachePreferences
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BasicAuthInterceptor @Inject constructor(
    val cachePreferences: CachePreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (chain.request().headers().names().contains("Authorization"))
            return chain.proceed(chain.request())

        val accountModel = cachePreferences.getModel(AccountModel::class)

        val credential = Credentials.basic(
            accountModel?.username.toString(),
            accountModel?.authToken?.token.toString()
        )

        val request = chain.request().newBuilder().header("Authorization", credential).build()
        return chain.proceed(request)
    }
}