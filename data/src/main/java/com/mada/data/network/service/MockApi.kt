package com.mada.data.network.service

import com.mada.data.network.model.request.TransactionRequest
import com.mada.data.network.model.response.TransHistoryResponse
import com.mada.data.network.model.response.TransactionResponse
import retrofit2.http.*

interface MockApi {

    @POST("beers/random")
    suspend fun createTransaction(
        @Body transaction_data: TransactionRequest,
        @Header("Content-Language") language: String = "en",
        @Header("Accept") accept: String = "application/json"
    ): TransactionResponse

    @GET("beers")
    suspend fun getTransactions(
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): List<TransHistoryResponse>
}