package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class PathType(val value: String?) {
    NONE(null),
    JOB("job"),
    VIEW("view");

    companion object {
        fun get(value: String?): PathType = values().first { it.value == value }
    }
}