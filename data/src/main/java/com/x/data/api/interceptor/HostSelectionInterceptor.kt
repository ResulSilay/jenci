package com.x.data.api.interceptor

import com.x.data.util.HttpExtension.toHttpUrl
import com.x.data.util.HttpExtension.toPathSegments
import com.x.domain.model.AccountModel
import com.x.domain.repository.cache.CachePreferences
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HostSelectionInterceptor @Inject constructor(
    private val cachePreferences: CachePreferences,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accountModel = cachePreferences.getModel(AccountModel::class)

        if (accountModel?.host != null) {
            val httpUrl = accountModel.host.toHttpUrl()

            if (httpUrl != null) {
                val urlBuild = request.url()
                    .newBuilder()
                    .scheme(httpUrl.scheme())
                    .host(httpUrl.host())
                    .port(httpUrl.port())
                    .build()

                val newUrl = HttpUrl.Builder()
                    .scheme(urlBuild.scheme())
                    .host(urlBuild.host())
                    .port(urlBuild.port())
                    .addPathSegments(httpUrl.pathSegments().toPathSegments())
                    .addPathSegments(urlBuild.pathSegments().toPathSegments())
                    .query(urlBuild.query())

                request = request.newBuilder()
                    .url(newUrl.build())
                    .build()

                return chain.proceed(request)
            }
        }

        return chain.proceed(request)
    }
}