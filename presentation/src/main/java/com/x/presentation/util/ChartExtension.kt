package com.x.presentation.util

import com.x.domain.model.BuildChartModel

fun BuildChartModel?.chartData(): Pair<List<Float>, List<Float>> {
    val isBuildsData = this.isNotEmpty()
    var x: List<Float> = listOf(1.0F, 2.0F, 1.0F)
    var y: List<Float> = listOf(2.0F, 2.0F, 2.0F)

    if (isBuildsData) {
        if (this != null &&
            this.isNotEmpty() &&
            this.items != null &&
            this.items?.isNotEmpty() == true
        ) {
            val itemsX = this.items
            val itemsY = this.items
            val xList = itemsX?.reversed()?.mapNotNull { it.day?.toFloat() }
            val yList = itemsY?.mapNotNull { it.buildCount?.toFloat() }

            if (xList?.isNotEmpty() == true && yList?.isNotEmpty() == true) {
                x = xList
                y = yList
            }
        }
    }

    return Pair(x, y)
}

fun BuildChartModel?.isNotEmpty(): Boolean {
    return this != null && this.items?.isNotEmpty() == true
}