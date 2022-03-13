package com.x.domain.model

import androidx.annotation.Keep
import com.x.common.type.BuildStatusType
import com.x.common.type.JobType

@Keep
data class JobViewListModel(
    val name: String? = null,
    val jobs: List<JobListItemModel>? = null,
)

@Keep
data class JobListItemModel(
    val name: String? = null,
    val url: String? = null,
    val type: JobType? = JobType.FREE,
    val status: BuildStatusType? = BuildStatusType.NONE,
)