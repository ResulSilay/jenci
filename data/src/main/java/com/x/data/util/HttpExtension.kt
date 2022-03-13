package com.x.data.util

import okhttp3.HttpUrl

object HttpExtension {

    fun String?.toHttpUrl(): HttpUrl? {
        return this?.let { HttpUrl.parse(it) }
    }

    fun List<String>?.toPathSegments(): String {
        if (this != null && this.isNotEmpty()) {
            var path = ""
            this.forEach {
                path += "$it/"
            }
            return path.removeSuffix("/")
        }
        return ""
    }
}