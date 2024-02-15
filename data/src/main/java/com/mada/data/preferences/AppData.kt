package com.mada.data.preferences

import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
class AppData @Inject constructor(private val preferences: Preferences) {

    fun getAccessToken(): String {
        return preferences[Preferences.ACCESS_TOKEN, ""].toString()
    }
}