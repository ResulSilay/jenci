package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class StatsModel(
    var nodeCount: Int? = 0,
    var viewCount: Int? = 0,
    var jobCount: Int? = 0,
    var queueCount: Int? = 0,
    var queueList: List<QueueItemModel>? = null,
    var buildChartList: BuildChartModel? = null,
)