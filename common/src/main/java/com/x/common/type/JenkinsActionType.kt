package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class JenkinsActionType(val value: Int) {
    NONE(-1),
    RESTART(0),
    SAFE_RESTART(1),
    SHUTDOWN(2),
    SAFE_SHUTDOWN(3),
    QUIET_DOWN(4),
    CANCEL_QUIET_DOWN(5);

    companion object {
        fun get(value: Int): JenkinsActionType? = values().firstOrNull { it.value == value }
    }
}