package com.mada.data.network.model.response

data class TransactionResponse(
    val amount: String = "",
    val commission: Double = 1.0,
    val created_at: String = "",
    val id: Int = 1,
    val ref_number: String = "",
    val status: String = "",
    val total_amount: String = "",
    val transaction_data: TransactionDataResponse = TransactionDataResponse(""),
    val user_name: String = "",
    val vat: Double = 0.0
)

data class TransactionDataResponse(
    val cart_number: String
)
