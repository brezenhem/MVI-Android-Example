package com.mada.domain.create_transaction.model

data class TransactionModel(
    val amount: String,
    val commission: Double,
    val createdAt: String,
    val id: Int,
    val refNumber: String,
    val status: String,
    val totalAmount: String,
    val userName: String,
    val vat: Double
)
