package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class ResponseModel(
    val code: Int = 0,
    val body: String? = null,
)
