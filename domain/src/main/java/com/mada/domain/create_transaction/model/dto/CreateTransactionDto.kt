package com.mada.domain.create_transaction.model.dto

data class CreateTransactionDto(
    val refNumber: String,
    val totalAmount: String,
    val cartNumber: String
)
