package com.x.domain.repository.cache

import kotlin.reflect.KClass

interface CachePreferences {

    fun <T : Comparable<T>> setModel(key: String, clazz: KClass<T>)

    fun <T : Any> setModel(model: T)

    fun <T : Any> getModel(clazz: KClass<T>): T?

    fun setString(name: String, value: String?)

    fun getString(name: String): String?

    fun setInt(name: String, value: Int?)

    fun getInt(name: String): Int

    fun <T : Any> remove(model: T)

    fun remove(key: String)

    fun clear()
}

inline fun <reified T : KClass<T>> CachePreferences.get(): T? = getModel(T::class)