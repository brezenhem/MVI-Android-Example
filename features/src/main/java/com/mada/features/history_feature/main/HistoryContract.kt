package com.mada.features.history_feature.main

import androidx.paging.PagingData
import com.mada.domain.load_transactions_history.model.TransHistoryModel
import com.mada.softpos.core.arch.MviEffect
import com.mada.softpos.core.arch.MviEvent
import com.mada.softpos.core.arch.MviState
import com.mada.softpos.core.domain.ErrorResult

class HistoryContract {
    sealed class Event : MviEvent {
        object OnLoadTitle : Event()
    }

    sealed class Effect : MviEffect {
        data class ShowError(val error: ErrorResult) : Effect()
    }

    data class State(
        val title: String,
        val isLoading: Boolean,
        val items: PagingData<TransHistoryModel>
    ) : MviState
}