package com.mada.features.management_feature.main

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.load_management.LoadManagementUseCase
import com.mada.features.more_feature.main.MoreContract
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

class ManagementVM @AssistedInject constructor(
    @Assisted var state: ManagementContract.State,
    private val loadManagementUseCase: LoadManagementUseCase
) : MviViewModel<ManagementContract.Event, ManagementContract.Effect, ManagementContract.State>(
    state
) {

    init {
        loadItems()
    }

    override fun handleEvent(event: ManagementContract.Event) {
        when (event) {
            is ManagementContract.Event.OnItemClicked -> itemClicked()
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            loadManagementUseCase(Unit).collectLatest { result ->
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
                        MoreContract.Effect.ShowError(result.exception.asResult())
                    }
                }
            }
        }
    }

    private fun itemClicked() {
        //  TODO: Implement it in future
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ManagementVM, ManagementContract.State> {
        override fun create(state: ManagementContract.State): ManagementVM
    }

    companion object :
        MavericksViewModelFactory<ManagementVM, ManagementContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): ManagementContract.State {
            return ManagementContract.State(
                isLoading = false,
                items = emptyList()
            )
        }
    }
}