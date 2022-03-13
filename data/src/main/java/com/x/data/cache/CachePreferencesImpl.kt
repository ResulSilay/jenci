package com.x.data.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.x.data.util.GsonHelper
import com.x.data.util.MoshiHelper
import com.x.domain.repository.cache.CachePreferences
import kotlin.reflect.KClass

class CachePreferencesImpl(context: Context) : CachePreferences {

    private val fileName = "jenci-cache"

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        fileName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var editor: SharedPreferences.Editor = preferences.edit()

    override fun <T : Comparable<T>> setModel(key: String, clazz: KClass<T>) {
        MoshiHelper.toJson(clazz)?.let { setString(key, it) }
    }

    override fun <T : Any> setModel(model: T) {
        setString(model.javaClass.name, GsonHelper.toJson(model))
    }

    override fun <T : Any> getModel(clazz: KClass<T>): T? {
        return getString(clazz.java.name)?.let {
            GsonHelper.fromJson(it, clazz)
        }
    }

    override fun setString(name: String, value: String?) {
        editor.putString(name, value)
        editor.commit()
    }

    override fun setInt(name: String, value: Int?) {
        if (value != null) {
            editor.putInt(name, value)
            editor.apply()
        }
    }

    override fun getString(name: String): String? {
        return preferences.getString(name, null)
    }

    override fun getInt(name: String): Int {
        return preferences.getInt(name, 0)
    }

    override fun <T : Any> remove(model: T) {
        editor.remove(model.javaClass.name)
    }

    override fun remove(key: String) {
        editor.remove(key)
    }

    override fun clear() {
        editor.clear().commit()
    }
}