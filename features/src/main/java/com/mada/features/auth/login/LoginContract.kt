package com.mada.features.auth.login

import com.mada.softpos.core.arch.MviEffect
import com.mada.softpos.core.arch.MviEvent
import com.mada.softpos.core.arch.MviState
import com.mada.softpos.core.domain.ErrorResult

class LoginContract {
    sealed class Event : MviEvent {
        class StartLogin(val username: String, val password: String) : Event()
    }

    sealed class Effect : MviEffect {
        data class ShowError(val error: ErrorResult) : Effect()
        object UserLoggedIn : Effect()
    }

    data class State(val isLoading: Boolean) : MviState
}