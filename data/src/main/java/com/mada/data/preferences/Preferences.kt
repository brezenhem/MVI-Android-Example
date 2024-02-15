package com.mada.data.preferences

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
class Preferences @Inject internal constructor(private val sharedPreferences: SharedPreferences) {

    fun put(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun put(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun put(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    operator fun get(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun removeAll() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val ACCESS_TOKEN: String = "ACCESS_TOKEN"
    }
}