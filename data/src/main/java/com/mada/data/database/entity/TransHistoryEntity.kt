package com.mada.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mada.data.network.model.response.TransHistoryResponse

@Entity(tableName = "transactions")
data class TransHistoryEntity(
    @PrimaryKey var id: Long,
    var type: String,
    var amount: String,
    var date: String,
    var page: Int
) {
    constructor(response: TransHistoryResponse) : this(
        response.id,
        "Some description",
        "0",
        response.date ?: "",
        0
    )
}