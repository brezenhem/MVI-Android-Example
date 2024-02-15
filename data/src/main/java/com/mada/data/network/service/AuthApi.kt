package com.mada.data.network.service

import com.mada.data.network.model.response.BaseResponse
import com.mada.data.network.model.response.AuthResponse
import retrofit2.http.*

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): BaseResponse<AuthResponse>
}