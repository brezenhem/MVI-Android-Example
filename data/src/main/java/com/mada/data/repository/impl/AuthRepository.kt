package com.mada.data.repository.impl

import com.mada.data.network.model.response.AuthResponse
import com.mada.data.network.model.response.LogoutResponse
import com.mada.data.network.service.AuthApi
import com.mada.data.network.utils.suspendApiCall
import com.mada.softpos.core.domain.SuspendResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(username: String, password: String): SuspendResult<AuthResponse>
    suspend fun logout(): SuspendResult<LogoutResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(username: String, password: String) = suspendApiCall(dispatcher) {
        authApi.login(username, password).data
    }

    override suspend fun logout() = suspendApiCall(dispatcher) {
        LogoutResponse(200, "Success")
    }
}

class AuthRepositoryMockImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val localDataRepository: LocalDataRepository
) : AuthRepository {
    override suspend fun login(username: String, password: String) = suspendApiCall(dispatcher) {
        delay(1000)
        val response = AuthResponse("1", "mocked_token", "mocked_refresh_token")
        localDataRepository.saveAccessToken(response.accessToken)
        response
    }

    override suspend fun logout() = suspendApiCall(dispatcher) {
        LogoutResponse(200, "Success")
    }
}
