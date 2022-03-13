package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.*

@Keep
data class JobBuildResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "jobs") var jobs: List<JobBuildsDataModel>? = arrayListOf(),
)

@Keep
data class JobBuildsDataModel(
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "builds") var builds: List<JobBuildDetailDataModel>? = arrayListOf(),
)

@Keep
data class JobBuildDetailDataModel(
    @field:Json(name = "id") var id: String? = null,
    @field:Json(name = "queueId") var queueId: Int? = null,
    @field:Json(name = "number") var number: Int? = null,
    @field:Json(name = "fullDisplayName") var fullDisplayName: String? = null,
    @field:Json(name = "displayName") var displayName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "result") var result: String? = null,
    @field:Json(name = "builtOn") var builtOn: String? = null,
    @field:Json(name = "building") var building: Boolean? = null,
    @field:Json(name = "keepLog") var keepLog: Boolean? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "duration") var duration: Long? = null,
    @field:Json(name = "estimatedDuration") var estimatedDuration: Long? = null,
)

@Keep
fun JobBuildResponse.toModel(): JobBuildModel {
    return JobBuildModel(
        jobs = jobs?.map { buildsIt ->
            JobBuildsItemModel().apply {
                builds = buildsIt.builds?.map { buildDetailIt ->
                    JobBuildDetailModel().apply {
                        number = buildDetailIt.number
                        duration = buildDetailIt.duration
                        result = buildDetailIt.result
                        timestamp = buildDetailIt.timestamp
                        estimatedDuration = buildDetailIt.estimatedDuration
                    }
                }
            }
        }
    )
}