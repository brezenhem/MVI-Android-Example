package com.mada.data.network.model.request

data class TransactionRequest(
    val ref_number: String,
    val total_amount: String,
    val transaction_data: TransactionDataRequest,
)

data class TransactionDataRequest(
    val cart_number: String
)