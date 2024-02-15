package com.mada.domain.load_transactions_history

import androidx.paging.*
import com.mada.data.database.entity.TransHistoryEntity
import com.mada.data.repository.impl.TransactionsRepository
import com.mada.softpos.core.domain.PagingUseCase
import com.mada.domain.load_transactions_history.model.TransHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class LoadTransactionsHistoryUseCase :
    PagingUseCase<TransHistoryEntity, TransHistoryModel>()

@OptIn(ExperimentalPagingApi::class)
class LoadTransactionsHistoryUseCaseImpl(
    private val repository: TransactionsRepository
) : LoadTransactionsHistoryUseCase() {

    override fun execute(): Flow<PagingData<TransHistoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                initialLoadSize = 40,
                enablePlaceholders = true
            ),
            remoteMediator = repository.getRemoteMediator()
        ) {
            repository.getTransHistoryPagingSource()
        }.flow.map { pagingData -> pagingData.map { it.mapper() } }
    }

    override fun TransHistoryEntity.mapper() = TransHistoryModel(
        id = id,
        type = type,
        amount = amount,
        date = date
    )
}
