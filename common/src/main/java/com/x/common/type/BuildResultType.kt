package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class BuildResultType(val value: String?) {
    NONE(null),
    FAILURE("FAILURE"),
    SUCCESS("SUCCESS");

    companion object {
        fun get(value: String?): BuildResultType = values().first { it.value == value }
    }
}