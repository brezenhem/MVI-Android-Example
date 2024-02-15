package com.mada.data.network.model.response

data class AuthResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)
