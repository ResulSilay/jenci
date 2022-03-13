package com.x.common.extension

import java.net.URLDecoder
import java.net.URLEncoder

fun String?.urlEncode(): String {
    return URLEncoder.encode(this, Charsets.UTF_8.name())
}

fun String?.urlDecode(): String {
    return URLDecoder.decode(this, Charsets.UTF_8.name())
}