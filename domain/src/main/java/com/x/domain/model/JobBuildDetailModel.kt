package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class JobBuildDetailModel(
    var id: String? = null,
    var queueId: Int? = null,
    var number: Int? = null,
    var fullDisplayName: String? = null,
    var description: String? = null,
    var displayName: String? = null,
    var result: String? = null,
    var building: Boolean? = null,
    var keepLog: Boolean? = null,
    var duration: Long? = null,
    var timestamp: Long? = null,
    var estimatedDuration: Long? = null,
    var artifacts: List<JobBuildArtifactModel>? = null,
    var consoleLog: String? = null,
)

@Keep
data class JobBuildArtifactModel(
    var fileName: String? = null,
)