package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.AuthCrumbModel

@Keep
data class CrumbResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "crumb") var crumb: String? = null,
    @field:Json(name = "crumbRequestField") var crumbRequestField: String? = null,
)

@Keep
fun CrumbResponse.toModel(): AuthCrumbModel {
    return AuthCrumbModel(crumb = crumb)
}