package com.x.data.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

object MoshiHelper {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getMoshiBuild(): Moshi {
        return moshi
    }

    fun <T> fromJson(json: String, type: Type): T? {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
        return jsonAdapter.fromJson(json)
    }

    inline fun <reified T> toJson(model: T): String? {
        val adapter = getMoshiBuild().adapter(T::class.java)
        return adapter.toJson(model)
    }
}