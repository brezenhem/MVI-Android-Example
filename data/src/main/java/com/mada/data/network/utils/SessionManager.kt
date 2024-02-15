package com.mada.data.network.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private val _events = MutableSharedFlow<SessionEvent>()
    val events = _events.asSharedFlow()

    fun logout() = runBlocking {
        _events.emit(SessionEvent.LogoutEvent)
    }
}

sealed class SessionEvent {
    object LogoutEvent : SessionEvent()
}