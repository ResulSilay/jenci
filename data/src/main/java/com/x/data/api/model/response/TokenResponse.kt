package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.AuthTokenModel

@Keep
data class TokenResponse(
    @field:Json(name = "status") var status: String? = null,
    @field:Json(name = "data") var data: TokenDataModel? = null,
)

@Keep
data class TokenDataModel(
    @field:Json(name = "tokenName") var tokenName: String? = null,
    @field:Json(name = "tokenUuid") var tokenUuid: String? = null,
    @field:Json(name = "tokenValue") var tokenValue: String? = null,
)

@Keep
fun TokenResponse.toModel(): AuthTokenModel {
    return AuthTokenModel(
        name = data?.tokenName.toString(),
        uuid = data?.tokenUuid.toString(),
        token = data?.tokenValue.toString(),
    )
}