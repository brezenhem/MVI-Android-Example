package com.mada.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("code")
    val code: Int,
    @SerializedName("errors")
    val errors: ErrorResponse,
    @SerializedName("message")
    val message: String
)