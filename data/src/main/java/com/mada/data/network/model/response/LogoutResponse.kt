package com.mada.data.network.model.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)