package com.mada.softpos.app

import android.app.Application
import com.mada.softpos.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

abstract class AbstractApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            //  TODO: Implement it in future
//            Timber.plant(CrashReportingTree())
        }
    }
}