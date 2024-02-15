package com.mada.softpos.core.arch

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

abstract class AbstractActivity : AppCompatActivity() {

    open fun setDecorFitsSystemWindows(decorFitsSystemWindows: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows)
    }
}