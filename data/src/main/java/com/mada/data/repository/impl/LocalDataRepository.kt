package com.mada.data.repository.impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.mada.data.preferences.Preferences
import com.mada.data.preferences.Preferences.Companion.ACCESS_TOKEN
import javax.inject.Inject

interface LocalDataRepository {
    fun accessToken(): String?
    fun saveAccessToken(token: String?)
    fun clearSession()
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
class LocalDataRepositoryImpl @Inject constructor(
    private val preferences: Preferences
) : LocalDataRepository {

    override fun accessToken(): String? {
        return preferences[ACCESS_TOKEN, null]

    }

    override fun saveAccessToken(token: String?) {
        preferences.put(ACCESS_TOKEN, token)
    }

    override fun clearSession() {
        preferences.put(ACCESS_TOKEN, null)
    }
}
