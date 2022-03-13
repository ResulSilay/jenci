package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.NodeComputerModel
import com.x.domain.model.NodeModel

@Keep
data class NodeResponse(
    @field:Json(name = "nodeName") var nodeName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "totalExecutors") var totalExecutors: Int? = null,
    @field:Json(name = "computer") var computer: List<NodeComputerDataModel>? = arrayListOf(),
)

@Keep
data class NodeComputerDataModel(
    @field:Json(name = "displayName") var displayName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "assignedLabels") var assignedLabels: List<NodeLabelDataModel>? = arrayListOf(),
)

@Keep
data class NodeLabelDataModel(
    @field:Json(name = "name") var name: String? = null,
)

@Keep
fun NodeResponse.toModel(): NodeModel {
    return NodeModel(
        computer = computer?.map { node ->
            NodeComputerModel(
                label = node.assignedLabels?.get(0)?.name,
                displayName = node.displayName,
                description = node.description
            )
        },
        totalExecutors = totalExecutors
    )
}