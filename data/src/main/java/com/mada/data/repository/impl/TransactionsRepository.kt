package com.mada.data.repository.impl

import androidx.paging.PagingSource
import com.mada.data.database.AppDatabase
import com.mada.data.database.entity.TransHistoryEntity
import com.mada.data.database.mediator.TransactionRemoteMediator
import com.mada.data.network.service.MockApi
import com.mada.data.network.model.request.TransactionRequest
import com.mada.data.network.model.response.TransactionResponse
import com.mada.data.network.utils.suspendApiCall
import com.mada.softpos.core.domain.SuspendResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface TransactionsRepository {
    suspend fun createTransaction(data: TransactionRequest): SuspendResult<TransactionResponse>
    fun getRemoteMediator(): TransactionRemoteMediator
    fun getTransHistoryPagingSource(): PagingSource<Int, TransHistoryEntity>
}

class TransactionsRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val serviceApi: MockApi,
    private val database: AppDatabase
) : TransactionsRepository {

    override fun getTransHistoryPagingSource() = database.transDao().getTransactions()

    override fun getRemoteMediator() = TransactionRemoteMediator(serviceApi, database)

    override suspend fun createTransaction(data: TransactionRequest) = suspendApiCall(dispatcher) {
        serviceApi.createTransaction(data)
    }
}
