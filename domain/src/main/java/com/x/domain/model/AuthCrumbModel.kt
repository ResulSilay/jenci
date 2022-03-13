package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class AuthCrumbModel(
    var crumb: String? = null,
)