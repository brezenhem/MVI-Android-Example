package com.mada.features.more_feature.main

import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.softpos.core.arch.MviEffect
import com.mada.softpos.core.arch.MviEvent
import com.mada.softpos.core.arch.MviState
import com.mada.softpos.core.domain.ErrorResult
import com.mada.softpos.core.ui.adapter.base.BaseItem

class MoreContract {
    sealed class Event : MviEvent {
        data class OnItemClicked(val data: LoadMoreModel.Type) : Event()
    }

    sealed class Effect : MviEffect {
        data class ShowError(val error: ErrorResult) : Effect()
    }

    data class State(
        val isLoading: Boolean,
        val items: List<BaseItem>
    ) : MviState
}