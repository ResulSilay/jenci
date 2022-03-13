package com.x.common.resource

import androidx.annotation.Keep

@Keep
sealed class Resource<out T>(
    val status: ResourceStatus,
    val code: Int?,
    val data: T?,
    val message: String?,
) {

    data class Success<out R>(val _code: Int?, val _data: R?) : Resource<R>(
        status = ResourceStatus.SUCCESS,
        code = _code,
        data = _data,
        message = null
    )

    data class Failure(val _code: Int?, val _message: String?) : Resource<Nothing>(
        status = ResourceStatus.FAILURE,
        code = _code,
        data = null,
        message = _message
    )

    data class Loading(val isLoading: Boolean) : Resource<Nothing>(
        status = ResourceStatus.LOADING,
        code = null,
        data = null,
        message = null
    )
}