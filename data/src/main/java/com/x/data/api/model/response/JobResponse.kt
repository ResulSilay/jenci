package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.common.extension.urlToPath
import com.x.common.type.BuildActionType
import com.x.common.type.BuildResultType
import com.x.common.type.BuildStatusType
import com.x.common.type.JobType
import com.x.domain.model.*

@Keep
data class JobResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "actions") var actions: List<JobActionDataModel>? = listOf(),
    @field:Json(name = "property") var property: List<JobActionDataModel>? = listOf(),
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "fullDisplayName") var fullDisplayName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "buildable") var buildable: Boolean? = null,
    @field:Json(name = "inQueue") var inQueue: Boolean? = null,
    @field:Json(name = "keepDependencies") var keepDependencies: Boolean? = null,
    @field:Json(name = "concurrentBuild") var concurrentBuild: Boolean? = null,
    @field:Json(name = "disabled") var disabled: Boolean? = null,
    @field:Json(name = "color") var color: String? = null,
    @field:Json(name = "url") var url: String? = null,
    @field:Json(name = "nextBuildNumber") var nextBuildNumber: Int? = null,
    @field:Json(name = "firstBuild") var firstBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastBuild") var lastBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastCompletedBuild") var lastCompletedBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastFailedBuild") var lastFailedBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastStableBuild") var lastStableBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastSuccessfulBuild") var lastSuccessfulBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastUnstableBuild") var lastUnstableBuild: JobBuildDataModel? = null,
    @field:Json(name = "lastUnsuccessfulBuild") var lastUnsuccessfulBuild: JobBuildDataModel? = null,
    @field:Json(name = "healthReport") var healthReport: List<JobHealthReportDataModel>? = listOf(),
    @field:Json(name = "builds") var builds: List<JobBuildDataModel>? = listOf(),
    @field:Json(name = "jobs") var jobs: List<ViewJobDataModel>? = listOf(),
)

@Keep
data class JobBuildDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "number") var number: Int? = null,
    @field:Json(name = "duration") var duration: Long? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "result") var result: String? = null,
    @field:Json(name = "url") var url: String? = null,
)

@Keep
data class JobHealthReportDataModel(
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "iconClassName") var iconClassName: String? = null,
    @field:Json(name = "iconUrl") var iconUrl: String? = null,
    @field:Json(name = "score") var score: Int? = null,
)

@Keep
data class JobActionDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "parameterDefinitions") var parameterDefinitions: List<JobActionParameterDataModel>? = listOf(),
)

@Keep
data class JobActionParameterDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "defaultParameterValue") var defaultParameterValue: JobActionParameterValueDataModel? = null,
    @field:Json(name = "type") var type: String? = null,
)

@Keep
data class JobActionParameterValueDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "value") var value: Any? = null,
)

@Keep
fun JobResponse.toModel(): JobModel {
    val actionsModel =
        if (this.actions != null &&
            this.actions?.isNotEmpty() == true &&
            this.actions?.get(0) != null &&
            this.actions?.get(0)?.parameterDefinitions?.isNotEmpty() == true
        )
            actions
        else if (this.property != null &&
            this.property?.isNotEmpty() == true &&
            this.property?.get(0) != null &&
            this.property?.get(0)?.parameterDefinitions?.isNotEmpty() == true
        )
            property
        else
            null

    return JobModel(
        name = name,
        url = url.urlToPath(),
        fullDisplayName = fullDisplayName,
        status = BuildStatusType.get(value = color),
        disabled = disabled == true,
        buildable = buildable == true,
        inQueue = inQueue == true,
        keepDependencies = keepDependencies == true,
        concurrentBuild = concurrentBuild == true,
        nextBuildNumber = nextBuildNumber,
        actions = actionsModel?.map { action ->
            JobActionModel(
                className = action.className,
                parameterDefinitions = action.parameterDefinitions?.map { actionParameter ->
                    JobActionParameterModel(
                        className = actionParameter.className,
                        name = actionParameter.name,
                        description = actionParameter.description,
                        defaultParameterValue = JobActionParameterValueModel(
                            className = actionParameter.defaultParameterValue?.className,
                            value = actionParameter.defaultParameterValue?.value?.toString()
                        ),
                        type = BuildActionType.get(actionParameter.type)
                    )
                }
            )
        },
        jobs = jobs?.map { job ->
            JobListItemModel(
                name = job.name,
                url = job.url.urlToPath(),
                type = JobType.get(job._class),
                status = BuildStatusType.get(job.color)
            )
        },
        buildItems = builds?.map { build ->
            JobBuildItemModel(
                number = build.number,
                duration = build.duration,
                timestamp = build.timestamp,
                result = BuildResultType.get(build.result.toString())
            )
        },
        healthReport = if (healthReport?.isNotEmpty() == true)
            healthReport?.get(0)?.let { healthReport ->
                JobHealthReportModel(
                    description = healthReport.description,
                    iconClassName = healthReport.iconClassName,
                    score = healthReport.score,
                )
            } else null,
        firstBuild = JobBuildItemModel().apply {
            className = firstBuild?.className
            number = firstBuild?.number
            duration = firstBuild?.duration
            timestamp = firstBuild?.timestamp
            result = BuildResultType.get(firstBuild?.result)
        },
        lastBuild = JobBuildItemModel().apply {
            className = lastBuild?.className
            number = lastBuild?.number
            duration = firstBuild?.duration
            timestamp = lastBuild?.timestamp
            result = BuildResultType.get(lastBuild?.result)
        },
        lastCompletedBuild = JobBuildItemModel().apply {
            className = lastCompletedBuild?.className
            number = lastCompletedBuild?.number
            duration = lastCompletedBuild?.duration
            timestamp = lastCompletedBuild?.timestamp
            result = BuildResultType.get(lastCompletedBuild?.result)
        },
        lastFailedBuild = JobBuildItemModel().apply {
            className = lastFailedBuild?.className
            number = lastFailedBuild?.number
            duration = lastFailedBuild?.duration
            timestamp = lastFailedBuild?.timestamp
            result = BuildResultType.get(lastFailedBuild?.result)
        },
        lastStableBuild = JobBuildItemModel().apply {
            className = lastStableBuild?.className
            number = lastStableBuild?.number
            duration = lastStableBuild?.duration
            timestamp = lastStableBuild?.timestamp
            result = BuildResultType.get(lastStableBuild?.result)
        },
        lastSuccessfulBuild = JobBuildItemModel().apply {
            className = lastSuccessfulBuild?.className
            number = lastSuccessfulBuild?.number
            duration = lastSuccessfulBuild?.duration
            timestamp = lastSuccessfulBuild?.timestamp
            result = BuildResultType.get(lastSuccessfulBuild?.result)
        },
        lastUnstableBuild = JobBuildItemModel().apply {
            className = lastUnstableBuild?.className
            number = lastUnstableBuild?.number
            duration = lastUnstableBuild?.duration
            timestamp = lastUnstableBuild?.timestamp
            result = BuildResultType.get(lastUnstableBuild?.result)
        },
        lastUnsuccessfulBuild = JobBuildItemModel().apply {
            className = lastUnsuccessfulBuild?.className
            number = lastUnsuccessfulBuild?.number
            duration = lastUnsuccessfulBuild?.duration
            timestamp = lastUnsuccessfulBuild?.timestamp
            result = BuildResultType.get(lastUnsuccessfulBuild?.result)
        },
    )
}