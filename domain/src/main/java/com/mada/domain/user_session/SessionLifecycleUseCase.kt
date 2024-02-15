package com.mada.domain.user_session

import com.mada.data.network.utils.SessionEvent
import com.mada.data.network.utils.SessionManager
import com.mada.data.repository.impl.AuthRepository
import com.mada.data.repository.impl.LocalDataRepository
import com.mada.softpos.core.domain.SharedFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

abstract class SessionLifecycleUseCase : SharedFlowUseCase<Unit, SessionEvent>() {
    abstract fun logout()
}

class SessionLifecycleUseCaseImpl(
    private val authRepository: AuthRepository,
    private val dataRepository: LocalDataRepository,
    private val manager: SessionManager
) : SessionLifecycleUseCase() {

    override fun execute(parameter: Unit): Flow<SessionEvent> = manager.events.onEach { event ->
        when (event) {
            SessionEvent.LogoutEvent -> {
                authRepository.logout()
                dataRepository.clearSession()
            }
        }
    }

    override fun logout() {
        manager.logout()
    }
}
