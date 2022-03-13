package com.x.data.api.interceptor

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*

class CookieJarInterceptor : CookieJar {
    private var cookies: List<Cookie>? = null

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        if (cookies != null) {
            return cookies as List<Cookie>
        }
        return Collections.emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies = ArrayList(cookies)
    }
}