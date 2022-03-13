package com.x.domain.model

import androidx.annotation.Keep
import com.x.common.type.BuildActionType
import com.x.common.type.BuildResultType
import com.x.common.type.BuildStatusType

@Keep
data class JobModel(
    var actions: List<JobActionModel>? = null,
    val jobs: List<JobListItemModel>? = null,
    val name: String? = null,
    val url: String? = null,
    val fullDisplayName: String? = null,
    val status: BuildStatusType? = null,
    val buildable: Boolean = false,
    val disabled: Boolean = false,
    val inQueue: Boolean = false,
    val keepDependencies: Boolean = false,
    val concurrentBuild: Boolean = false,
    val nextBuildNumber: Int? = null,
    var healthReport: JobHealthReportModel? = null,
    var buildItems: List<JobBuildItemModel>? = arrayListOf(),
    var firstBuild: JobBuildItemModel? = null,
    var lastBuild: JobBuildItemModel? = null,
    var lastCompletedBuild: JobBuildItemModel? = null,
    var lastFailedBuild: JobBuildItemModel? = null,
    var lastStableBuild: JobBuildItemModel? = null,
    var lastSuccessfulBuild: JobBuildItemModel? = null,
    var lastUnstableBuild: JobBuildItemModel? = null,
    var lastUnsuccessfulBuild: JobBuildItemModel? = null,
)

@Keep
data class JobActionModel(
    var className: String? = null,
    var parameterDefinitions: List<JobActionParameterModel>? = null,
)

@Keep
data class JobActionParameterModel(
    var className: String? = null,
    var name: String? = null,
    var description: String? = null,
    var defaultParameterValue: JobActionParameterValueModel? = null,
    var type: BuildActionType? = null,
)

@Keep
data class JobActionParameterValueModel(
    var className: String? = null,
    var value: String? = null,
)

@Keep
data class JobBuildItemModel(
    var className: String? = null,
    var number: Int? = null,
    var duration: Long? = null,
    var timestamp: Long? = null,
    var result: BuildResultType = BuildResultType.NONE,
)

@Keep
data class JobHealthReportModel(
    var description: String? = null,
    var iconClassName: String? = null,
    var score: Int? = null,
)