package com.mada.features.history_feature.main

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.load_transactions_history.LoadTransactionsHistoryUseCase
import com.mada.softpos.core.arch.MviViewModel
import com.mada.softpos.core.di.AssistedViewModelFactory
import com.mada.softpos.core.di.hiltMavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryVM @AssistedInject constructor(
    @Assisted var state: HistoryContract.State,
    private val transactionsHistoryUseCase: LoadTransactionsHistoryUseCase
) : MviViewModel<HistoryContract.Event, HistoryContract.Effect, HistoryContract.State>(
    state
) {

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            transactionsHistoryUseCase.execute()
                .cachedIn(viewModelScope)
                .collectLatest {
                    setState { copy(items = it) }
                }
        }
    }

    override fun handleEvent(event: HistoryContract.Event) {
        when (event) {
            is HistoryContract.Event.OnLoadTitle -> loadTitle()
        }
    }

    private fun loadTitle() {
        setState { copy(title = "In development") }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<HistoryVM, HistoryContract.State> {
        override fun create(state: HistoryContract.State): HistoryVM
    }

    companion object :
        MavericksViewModelFactory<HistoryVM, HistoryContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): HistoryContract.State {
            return HistoryContract.State(
                title = "",
                isLoading = false,
                PagingData.empty()
            )
        }
    }
}