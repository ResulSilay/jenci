package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.common.type.BuildStatusType
import com.x.domain.model.*

@Keep
data class QueueResponse(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "items") var items: List<QueueItemDataModel>? = arrayListOf(),
)

@Keep
data class QueueItemDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "id") var id: Int? = null,
    @field:Json(name = "why") var why: String? = null,
    @field:Json(name = "params") var params: String? = null,
    @field:Json(name = "stuck") var stuck: Boolean? = null,
    @field:Json(name = "buildable") var buildable: Boolean? = null,
    @field:Json(name = "blocked") var blocked: Boolean? = null,
    @field:Json(name = "inQueueSince") var inQueueSince: Long? = null,
    @field:Json(name = "timestamp") var timestamp: Long? = null,
    @field:Json(name = "actions") var actions: List<QueueItemActionDataModel>? = arrayListOf(),
    @field:Json(name = "task") var task: QueueItemTaskDataModel? = null,
    @field:Json(name = "url") var url: String? = null,
)

@Keep
data class QueueItemTaskDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "color") var color: String? = null,
    @field:Json(name = "url") var url: String? = null,
)

@Keep
data class QueueItemActionDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "causes") var causes: List<QueueItemCausesDataModel>? = null,
)

@Keep
data class QueueItemCausesDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "userId") var userId: String? = null,
    @field:Json(name = "userName") var userName: String? = null,
    @field:Json(name = "shortDescription") var shortDescription: String? = null,
)

@Keep
fun QueueResponse.toModel(): QueueModel {
    return QueueModel(
        items = items?.map { item ->
            QueueItemModel(
                id = item.id,
                why = item.why,
                params = item.params,
                stuck = item.stuck,
                buildable = item.buildable,
                blocked = item.blocked,
                inQueueSince = item.inQueueSince,
                timestamp = item.timestamp,
                actions = item.actions?.map { action ->
                    QueueItemActionModel().apply {
                        this.causes = action.causes?.map { cousesItem ->
                            QueueItemCausesModel().apply {
                                this.userId = cousesItem.userId
                                this.userName = cousesItem.userName
                                this.shortDescription = cousesItem.shortDescription
                            }
                        }
                    }
                },
                task = QueueItemTaskModel().apply {
                    this.name = item.task?.name
                    this.status = BuildStatusType.get(value = item.task?.color)
                },
            )
        },
        queueCount = items?.size
    )
}