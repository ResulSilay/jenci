package com.x.common.extension

import android.util.Base64

fun toBasicAuth(username: String, password: String): String {
    return "Basic ${("$username:$password").toBase64()}"
}

fun String?.toBase64(): String {
    return Base64.encodeToString(this?.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
}

fun String?.decodeBase64(): String {
    return Base64.decode(this?.toByteArray(Charsets.UTF_8), Base64.NO_WRAP).toString(Charsets.UTF_8)
}