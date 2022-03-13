package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.NodeDetailModel

@Keep
data class NodeDetailResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "displayName") var displayName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "idle") var idle: Boolean? = null,
    @field:Json(name = "jnlpAgent") var jnlpAgent: Boolean? = null,
    @field:Json(name = "launchSupported") var launchSupported: Boolean? = null,
    @field:Json(name = "manualLaunchAllowed") var manualLaunchAllowed: Boolean? = null,
    @field:Json(name = "monitorData") var monitorData: NodeDetailMonitorDataModel? = null,
    @field:Json(name = "temporarilyOffline") var temporarilyOffline: Boolean? = null,
    @field:Json(name = "numExecutors") var numExecutors: Int? = null,
)

@Keep
data class NodeDetailMonitorDataModel(
    @field:Json(name = "hudson.node_monitors.SwapSpaceMonitor") var swapSpaceMonitor: NodeDetailSwapSpaceDataModel? = null,
    @field:Json(name = "hudson.node_monitors.TemporarySpaceMonitor") var temporarySpaceMonitor: NodeDetailTemporarySpaceMonitorDataModel? = null,
    @field:Json(name = "hudson.node_monitors.DiskSpaceMonitor") var diskSpaceMonitor: NodeDetailDiskSpaceMonitorDataModel? = null,
    @field:Json(name = "hudson.node_monitors.ClockMonitor") var clockMonitor: NodeDetailClockMonitorDataModel? = null,
    @field:Json(name = "hudson.node_monitors.ResponseTimeMonitor") var responseTimeMonitor: NodeDetailResponseTimeMonitorDataModel? = null,
    @field:Json(name = "hudson.node_monitors.ArchitectureMonitor") var architectureMonitor: String? = null,
)

@Keep
data class NodeDetailSwapSpaceDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "availablePhysicalMemory") var availablePhysicalMemory: Int? = null,
    @field:Json(name = "availableSwapSpace") var availableSwapSpace: Long? = null,
    @field:Json(name = "totalPhysicalMemory") var totalPhysicalMemory: Int? = null,
    @field:Json(name = "totalSwapSpace") var totalSwapSpace: Long? = null,
)

@Keep
data class NodeDetailTemporarySpaceMonitorDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "size") var size: Long? = null,
)

@Keep
data class NodeDetailDiskSpaceMonitorDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "size") var size: Long? = null,
)

@Keep
data class NodeDetailResponseTimeMonitorDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "average") var average: Int? = null,
)

@Keep
data class NodeDetailClockMonitorDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "diff") var average: Int? = null,
)

@Keep
fun NodeDetailResponse.toModel(): NodeDetailModel {
    return NodeDetailModel(
        displayName = displayName,
        description = description,
        idle = idle,
        jnlpAgent = jnlpAgent,
        launchSupported = launchSupported,
        manualLaunchAllowed = manualLaunchAllowed,
        temporarilyOffline = temporarilyOffline,
        numExecutors = numExecutors,
    )
}