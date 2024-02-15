package com.mada.domain.load_transactions_history.model

data class TransHistoryModel(
    val id: Long,
    val type: String,
    val amount: String,
    val date: String
)
