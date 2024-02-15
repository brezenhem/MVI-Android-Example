package com.mada.domain.load_transactions

import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.softpos.core.domain.FlowUseCase
import com.mada.softpos.core.domain.SuspendResult
import com.mada.softpos.core.ui.adapter.items.MenuItem
import com.mada.softpos.domain.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow

abstract class LoadTransactionsUseCase(
    defaultDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, LoadMoreModel>(defaultDispatcher)

class LoadTransactionsUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher
) : LoadTransactionsUseCase(defaultDispatcher) {

    override fun execute(parameter: Unit) = flow {
        val items = listOf(
            MenuItem("Item 1", R.drawable.ic_menu_sale),
            MenuItem("Item 2", R.drawable.ic_menu_reversal),
            MenuItem("Item 3", R.drawable.ic_menu_refund),
            MenuItem("Item 4", R.drawable.ic_menu_cashback),
        )
        val result = LoadMoreModel(items)

        emit(SuspendResult.Success(result))
    }
}
