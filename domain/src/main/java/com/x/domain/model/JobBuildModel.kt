package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class JobBuildModel(
    var jobs: List<JobBuildsItemModel>? = arrayListOf(),
)

@Keep
data class JobBuildsItemModel(
    var name: String? = null,
    var builds: List<JobBuildDetailModel>? = arrayListOf(),
)