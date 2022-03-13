package com.x.domain.model

import androidx.annotation.Keep
import com.x.common.type.BuildStatusType

@Keep
data class QueueModel(
    val items: List<QueueItemModel>? = null,
    val queueCount: Int? = null,
)

@Keep
data class QueueItemModel(
    var id: Int? = null,
    var why: String? = null,
    var params: String? = null,
    var stuck: Boolean? = null,
    var buildable: Boolean? = null,
    var blocked: Boolean? = null,
    var inQueueSince: Long? = null,
    var timestamp: Long? = null,
    var actions: List<QueueItemActionModel>? = arrayListOf(),
    var task: QueueItemTaskModel? = null,
)

@Keep
data class QueueItemTaskModel(
    var name: String? = null,
    var status: BuildStatusType? = null,
)

@Keep
data class QueueItemActionModel(
    var causes: List<QueueItemCausesModel>? = null,
)

@Keep
data class QueueItemCausesModel(
    var userId: String? = null,
    var userName: String? = null,
    var shortDescription: String? = null,
)