package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class NodeDetailModel(
    var className: String? = null,
    var displayName: String? = null,
    var description: String? = null,
    var idle: Boolean? = null,
    var jnlpAgent: Boolean? = null,
    var launchSupported: Boolean? = null,
    var manualLaunchAllowed: Boolean? = null,
    var temporarilyOffline: Boolean? = null,
    var numExecutors: Int? = null,
)