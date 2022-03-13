package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class AuthTokenModel(
    val name: String? = null,
    val uuid: String? = null,
    val token: String? = null,
)