package com.mada.features.transactions_feature.main

import com.mada.softpos.core.arch.MviEffect
import com.mada.softpos.core.arch.MviEvent
import com.mada.softpos.core.arch.MviState
import com.mada.softpos.core.domain.ErrorResult
import com.mada.softpos.core.ui.adapter.base.BaseItem

class TransactionsContract {
    sealed class Event : MviEvent {
        object OnItemClicked : Event()
    }

    sealed class Effect : MviEffect {
        data class ShowError(val error: ErrorResult) : Effect()
    }

    data class State(
        val isLoading: Boolean,
        val items: List<BaseItem>
    ) : MviState
}