package com.mada.softpos.app_entry

import com.airbnb.mvrx.Mavericks
import com.mada.softpos.app.AbstractApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : AbstractApp() {

    override fun onCreate() {
        Mavericks.initialize(this)
        super.onCreate()
    }
}