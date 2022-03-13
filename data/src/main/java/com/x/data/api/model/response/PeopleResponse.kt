package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.PeopleModel
import com.x.domain.model.PeopleProjectItemModel
import com.x.domain.model.PeopleUserItemModel

@Keep
data class PeopleResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "users") var users: List<PeopleUsersDataModel>? = listOf(),
)

@Keep
data class PeopleUsersDataModel(
    @field:Json(name = "lastChange") var lastChange: String? = null,
    @field:Json(name = "project") var project: PeopleProjectItemDataModel? = null,
    @field:Json(name = "user") var user: PeopleUserItemDataModel? = null,
)

@Keep
data class PeopleUserItemDataModel(
    @field:Json(name = "absoluteUrl") var absoluteUrl: String? = null,
    @field:Json(name = "fullName") var fullName: String? = null,
)

@Keep
data class PeopleProjectItemDataModel(
    @field:Json(name = "name") var name: String? = null,
)

@Keep
fun PeopleResponse.toModel(): PeopleModel {
    return PeopleModel(
        users = users?.map { userList ->
            PeopleUserItemModel(
                fullName = userList.user?.fullName,
                project = userList.project?.let { project ->
                    PeopleProjectItemModel(
                        name = project.name
                    )
                },
                lastChange = userList.lastChange,
            )
        }
    )
}