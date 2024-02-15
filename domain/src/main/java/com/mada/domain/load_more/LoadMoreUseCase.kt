package com.mada.domain.load_more

import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.softpos.core.domain.FlowUseCase
import com.mada.softpos.core.domain.SuspendResult
import com.mada.softpos.core.ui.adapter.items.MenuItem
import com.mada.softpos.domain.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow

abstract class LoadMoreUseCase(
    defaultDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, LoadMoreModel>(defaultDispatcher)

class LoadMoreUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher
) : LoadMoreUseCase(defaultDispatcher) {

    override fun execute(parameter: Unit) = flow {
        val items = listOf(
            MenuItem("Logout", R.drawable.ic_menu_profile)
        )
        // add other menu items here
        val result = LoadMoreModel(items)

        emit(SuspendResult.Success(result))
    }
}
