package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class PeopleModel(
    val users: List<PeopleUserItemModel>? = null,
)

@Keep
data class PeopleUserItemModel(
    val fullName: String? = null,
    val lastChange: String? = null,
    val project: PeopleProjectItemModel? = null,
    val absoluteUrl: String? = null,
)

@Keep
data class PeopleProjectItemModel(
    val name: String? = null,
)