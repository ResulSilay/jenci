package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.UserModel

@Keep
data class UserResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "id") var id: String? = null,
    @field:Json(name = "fullName") var fullName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "property") var property: List<UserPropertyDataModel>? = null,
)

@Keep
data class UserPropertyDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "address") var address: String? = null,
    @field:Json(name = "insensitiveSearch") var insensitiveSearch: Boolean? = null,
)

@Keep
fun UserResponse.toModel(): UserModel {
    return UserModel(
        id = id,
        name = fullName,
        description = description
    )
}