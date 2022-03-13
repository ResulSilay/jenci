package com.x.data.util

import com.google.gson.Gson
import kotlin.reflect.KClass

object GsonHelper {

    private val gson = Gson()

    fun get() = gson

    fun <T> toJson(model: T): String? {
        return Gson().toJson(model)
    }

    fun <T : Any> fromJson(json: String, type: KClass<T>): T {
        return Gson().fromJson(json, type.java)
    }
}