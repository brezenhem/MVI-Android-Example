package com.mada.domain.login_user

import com.mada.data.network.model.response.AuthResponse
import com.mada.data.repository.impl.AuthRepository
import com.mada.domain.login_user.dto.LoginDto
import com.mada.softpos.core.domain.FlowUseCase
import com.mada.softpos.core.domain.SuspendResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow

abstract class UserLoginUseCase(
    defaultDispatcher: CoroutineDispatcher,
    val authRepository: AuthRepository
) : FlowUseCase<LoginDto, AuthResponse>(defaultDispatcher)

class UserLoginUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher,
    authRepository: AuthRepository
) : UserLoginUseCase(defaultDispatcher, authRepository) {

    override fun execute(parameter: LoginDto) = flow {
        emit(SuspendResult.Loading)
        emit(
            SuspendResult.Success(
                authRepository.login(
                    parameter.username,
                    parameter.password
                )
            ).data
        )
    }
}
