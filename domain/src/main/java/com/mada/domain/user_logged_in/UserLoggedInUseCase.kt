package com.mada.domain.user_logged_in

import com.mada.data.repository.impl.LocalDataRepository
import com.mada.softpos.core.domain.CallableResult
import com.mada.softpos.core.domain.CallableUseCase

abstract class UserLoggedInUseCase : CallableUseCase<Unit, Boolean>()

class UserLoggedInUseCaseImpl(
    private val localDataRepository: LocalDataRepository
) : UserLoggedInUseCase() {

    override fun execute(parameter: Unit): CallableResult<Boolean> {
        return CallableResult.Success(!localDataRepository.accessToken().isNullOrEmpty())
    }
}
