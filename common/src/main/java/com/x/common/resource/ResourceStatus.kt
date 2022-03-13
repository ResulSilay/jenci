package com.x.common.resource

import androidx.annotation.Keep

@Keep
enum class ResourceStatus {
    SUCCESS,
    FAILURE,
    LOADING,
    COMPLETED
}