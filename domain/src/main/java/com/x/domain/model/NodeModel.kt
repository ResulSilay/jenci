package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class NodeModel(
    var computer: List<NodeComputerModel>? = arrayListOf(),
    var totalExecutors: Int? = null,
)

@Keep
data class NodeComputerModel(
    val label: String? = null,
    var displayName: String? = null,
    var description: String? = null,
)