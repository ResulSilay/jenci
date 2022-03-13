package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.*

@Keep
data class JobBuildDetailResponse(
    @field:Json(name = "_class") var className: String? = null,
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
    @field:Json(name = "actions") var actions: List<QueueItemActionDataModel>? = arrayListOf(),
    @field:Json(name = "artifacts") var artifacts: List<JobBuildArtifactDataModel>? = arrayListOf(),
)

@Keep
data class JobBuildArtifactDataModel(
    @field:Json(name = "fileName") var fileName: String? = null,
    @field:Json(name = "displayPath") var displayPath: String? = null,
    @field:Json(name = "relativePath") var relativePath: String? = null,
)

@Keep
fun JobBuildDetailResponse.toModel(): JobBuildDetailModel {
    return JobBuildDetailModel(
        id = id,
        queueId = queueId,
        number = number,
        fullDisplayName = fullDisplayName,
        description = description,
        displayName = displayName,
        result = result,
        building = building,
        keepLog = keepLog,
        duration = duration,
        timestamp = timestamp,
        estimatedDuration = estimatedDuration,
        artifacts = artifacts?.map {
            JobBuildArtifactModel(
                fileName = it.fileName
            )
        }
    )
}