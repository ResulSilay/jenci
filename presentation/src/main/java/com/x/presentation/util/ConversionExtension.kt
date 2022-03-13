package com.x.presentation.util

fun List<Int?>?.toFloatList(): ArrayList<Float> {
    val items = ArrayList<Float>()
    this?.forEach {
        it?.let { it1 -> items.add(it1.toFloat()) }
    }
    return items
}