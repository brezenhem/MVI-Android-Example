package com.mada.domain.load_management

import com.mada.domain.load_management.model.LoadManagementModel
import com.mada.softpos.core.domain.FlowUseCase
import com.mada.softpos.core.domain.SuspendResult
import com.mada.softpos.core.ui.adapter.items.MenuItem
import com.mada.softpos.domain.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow

abstract class LoadManagementUseCase(
    defaultDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, LoadManagementModel>(defaultDispatcher)

class LoadManagementUseCaseImpl(
    defaultDispatcher: CoroutineDispatcher
) : LoadManagementUseCase(defaultDispatcher) {

    override fun execute(parameter: Unit) = flow {
        val items = listOf(
            MenuItem("Item title", R.drawable.ic_menu_report),
        )
        val result = LoadManagementModel(items)

        emit(SuspendResult.Success(result))
    }
}
