package com.x.domain.model

import androidx.annotation.Keep

@Keep
data class ViewItemModel(
    var name: String? = null,
    val url: String? = null,
)