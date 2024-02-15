package com.mada.features.main

import android.app.Activity
import android.content.Intent
import com.mada.softpos.core.arch.MviEffect
import com.mada.softpos.core.arch.MviEvent
import com.mada.softpos.core.arch.MviState

class MainContract {
    sealed class Event : MviEvent {
        data class OnKeepOnScreenCondition(val value: Boolean) : Event()
        data class OnCheckUserSignedIn(val activity: Activity) : Event()
    }

    sealed class Effect : MviEffect {
        data class SetKeepOnScreenCondition(val value: Boolean) : Effect()
        data class StartIntent(val intent: Intent) : Effect()
        object Logout : Effect()
    }

    data class State(
        val isLoading: Boolean,
    ) : MviState
}
