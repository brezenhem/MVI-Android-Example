package com.mada.data.database.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mada.data.database.AppDatabase
import com.mada.data.database.entity.TransHistoryEntity
import com.mada.data.network.service.MockApi

@OptIn(ExperimentalPagingApi::class)
class TransactionRemoteMediator(
    private val apiService: MockApi,
    private val database: AppDatabase
) : RemoteMediator<Int, TransHistoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TransHistoryEntity>
    ): MediatorResult {
        try {
            val currentPage = getLatestPageIndex(loadType, state) ?: return MediatorResult.Success(
                endOfPaginationReached = true
            )

            val response = apiService.getTransactions(
                currentPage,
                state.config.pageSize
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.transDao().clearAll()
                }
                database.transDao().insertAll(response.map {
                    TransHistoryEntity(it).apply { page = currentPage }
                })
            }

            return MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun getLatestPageIndex(
        loadType: LoadType,
        state: PagingState<Int, TransHistoryEntity>
    ): Int? {
        return when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE_INDEX
            LoadType.PREPEND -> return null
            LoadType.APPEND -> state.lastItemOrNull()?.page?.plus(1) ?: INITIAL_PAGE_INDEX
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
