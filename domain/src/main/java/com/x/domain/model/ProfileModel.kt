package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class ProfileModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val address: String? = null,
)
