package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class ViewModel(
    val name: String? = null,
    val views: List<ViewItemModel>? = null,
    val viewCount: Int? = null,
    val jobCount: Int? = null,
)