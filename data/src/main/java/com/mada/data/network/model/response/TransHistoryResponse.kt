package com.mada.data.network.model.response

import com.google.gson.annotations.SerializedName

data class TransHistoryResponse(
    var id: Long,
    @SerializedName("name") var type: String?,
    @SerializedName("attenuation_level") var amount: String?,
    @SerializedName("first_brewed") var date: String?
)
