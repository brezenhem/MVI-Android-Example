package com.mada.features.transactions_feature.main

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.load_transactions.LoadTransactionsUseCase
import com.mada.softpos.core.arch.MviViewModel
import com.mada.softpos.core.di.AssistedViewModelFactory
import com.mada.softpos.core.di.hiltMavericksViewModelFactory
import com.mada.softpos.core.domain.SuspendResult
import com.mada.softpos.core.domain.asResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionsVM @AssistedInject constructor(
    @Assisted var state: TransactionsContract.State,
    private val loadTransactionsUseCase: LoadTransactionsUseCase
) : MviViewModel<TransactionsContract.Event, TransactionsContract.Effect, TransactionsContract.State>(
    state
) {

    init {
        loadItems()
    }

    override fun handleEvent(event: TransactionsContract.Event) {
        when (event) {
            is TransactionsContract.Event.OnItemClicked -> itemClicked()
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            loadTransactionsUseCase(Unit).collectLatest { result ->
                when (result) {
                    is SuspendResult.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is SuspendResult.Success -> {
                        setState {
                            copy(
                                isLoading = false,
                                items = result.data.items
                            )
                        }
                    }

                    is SuspendResult.Failure -> {
                        TransactionsContract.Effect.ShowError(result.exception.asResult())
                    }
                }
            }
        }
    }

    private fun itemClicked() {
        //  TODO: Implement it in future
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TransactionsVM, TransactionsContract.State> {
        override fun create(state: TransactionsContract.State): TransactionsVM
    }

    companion object :
        MavericksViewModelFactory<TransactionsVM, TransactionsContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): TransactionsContract.State {
            return TransactionsContract.State(
                isLoading = false,
                items = emptyList()
            )
        }
    }
}