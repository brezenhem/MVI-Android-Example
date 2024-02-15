package com.mada.domain.user_logout

import com.mada.data.network.utils.SessionManager
import com.mada.softpos.core.domain.CallableResult
import com.mada.softpos.core.domain.CallableUseCase

abstract class UserLogoutUseCase : CallableUseCase<Unit, Unit>()

class UserLogoutUseCaseImpl(
    private val manager: SessionManager
) : UserLogoutUseCase() {

    override fun execute(parameter: Unit): CallableResult<Unit> {
        return CallableResult.Success(manager.logout())
    }
}
