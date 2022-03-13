package com.x.common.type

import androidx.annotation.Keep

@Keep
enum class DashboardStatsType(val value: Int) {
    NODES(0),
    VIEWS(1),
    QUEUES(2),
    JOBS(3);

    companion object {
        fun get(value: Int): DashboardStatsType? = values().firstOrNull { it.value == value }
    }
}