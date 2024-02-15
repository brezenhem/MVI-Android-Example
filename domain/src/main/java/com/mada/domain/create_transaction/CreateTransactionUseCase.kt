package com.mada.domain.create_transaction

import com.mada.data.network.model.request.TransactionDataRequest
import com.mada.data.network.model.request.TransactionRequest
import com.mada.data.network.model.response.TransactionResponse
import com.mada.data.repository.impl.TransactionsRepository
import com.mada.domain.create_transaction.model.TransactionModel
import com.mada.domain.create_transaction.model.dto.CreateTransactionDto
import com.mada.softpos.core.domain.FlowUseCase
import com.mada.softpos.core.domain.SuspendResult
import com.mada.softpos.core.domain.asModelValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class CreateTransactionUseCase(
    defaultDispatcher: CoroutineDispatcher,
) : FlowUseCase<CreateTransactionDto, TransactionModel>(defaultDispatcher)

class CreateTransactionUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher,
    private val transactionsRepository: TransactionsRepository,
) : CreateTransactionUseCase(defaultDispatcher) {

    override fun execute(parameter: CreateTransactionDto): Flow<SuspendResult<TransactionModel>> {
        return flow {
            emit(SuspendResult.Loading)
            val result = transactionsRepository.createTransaction(
                TransactionRequest(
                    parameter.refNumber,
                    parameter.totalAmount,
                    TransactionDataRequest(parameter.cartNumber)
                )
            )

            emit(result.asModelValue { response -> response.map() })
        }
    }

    private fun TransactionResponse.map() =
        TransactionModel(
            id = id,
            amount = amount,
            commission = commission,
            createdAt = created_at,
            refNumber = ref_number,
            status = status,
            totalAmount = total_amount,
            userName = user_name,
            vat = vat
        )
}