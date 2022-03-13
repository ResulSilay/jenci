package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class BuildChartModel(
    var items: List<BuildChartItemModel>? = null,
)

@Keep
data class BuildChartItemModel(
    var day: Int? = null,
    var buildCount: Int? = null,
)