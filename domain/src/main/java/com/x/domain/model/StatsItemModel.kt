package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class StatsItemModel(
    val title: String,
    val value: String,
)