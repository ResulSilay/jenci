package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
)
