package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class BuildStatusType(val value: String?) {
    NONE(null),
    FAILED("red"),
    FAILED_IN_PROGRESS("red_anime"),
    UNSTABLE("yellow"),
    UNSTABLE_IN_PROGRESS("yellow_anime"),
    SUCCESS("blue"),
    SUCCESS_IN_PROGRESS("blue_anime"),
    PENDING("grey"),
    PENDING_IN_PROGRESS("grey_anime"),
    DISABLED("disabled"),
    DISABLED_PROGRESS("disabled_anime"),
    ABORTED("aborted"),
    ABORTED_PROGRESS("aborted_anime"),
    NO_BUILT("nobuilt"),
    NO_BUILT_PROGRESS("nobuilt_anime"),
    NOT_BUILT("notbuilt"),
    NOT_BUILT_PROGRESS("notbuilt_anime");

    companion object {
        fun get(value: String?): BuildStatusType? = values().firstOrNull { it.value == value }
    }
}