package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.common.extension.urlToPath
import com.x.common.type.BuildStatusType
import com.x.common.type.JobType
import com.x.domain.model.JobListItemModel
import com.x.domain.model.JobViewListModel

@Keep
data class ViewJobResponse(
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "url") var url: String? = null,
    @field:Json(name = "jobs") var jobs: List<ViewJobDataModel>? = listOf(),
)

@Keep
data class ViewJobDataModel(
    @field:Json(name = "_class", ignore = true) var _class: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "url") var url: String? = null,
    @field:Json(name = "color") var color: String? = null,
)

@Keep
fun ViewJobResponse.toModel(): JobViewListModel {
    return JobViewListModel(
        name = name,
        jobs = jobs?.map { job ->

            JobListItemModel(
                name = job.name,
                url = job.url.urlToPath(),
                type = JobType.get(job._class),
                status = BuildStatusType.get(job.color)
            )
        }
    )
}