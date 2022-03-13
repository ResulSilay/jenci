package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class AccountModel(
    var accountId: Int? = null,
    var name: String? = null,
    var host: String? = null,
    var username: String? = null,
    var password: String? = null,
    var isSSL: Boolean? = false,
    var isBiometric: Boolean? = false,
    var authToken: AuthTokenModel? = null,
    var authCrumb: AuthCrumbModel? = null,
)