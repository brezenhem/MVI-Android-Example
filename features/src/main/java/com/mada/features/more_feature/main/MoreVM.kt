package com.mada.features.more_feature.main

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.load_more.LoadMoreUseCase
import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.domain.user_logout.UserLogoutUseCase
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

class MoreVM @AssistedInject constructor(
    @Assisted var state: MoreContract.State,
    private val loadMoreUseCase: LoadMoreUseCase,
    private var userLogoutUseCase: UserLogoutUseCase
) : MviViewModel<MoreContract.Event, MoreContract.Effect, MoreContract.State>(
    state
) {

    init {
        loadItems()
    }

    override fun handleEvent(event: MoreContract.Event) {
        when (event) {
            is MoreContract.Event.OnItemClicked -> itemClicked(event.data)
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            loadMoreUseCase(Unit).collectLatest { result ->
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

    private fun itemClicked(data: LoadMoreModel.Type) {
        when (data) {
            LoadMoreModel.Type.LOGOUT -> userLogoutUseCase.execute(Unit)
            else -> {
                // TODO handle other actions
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MoreVM, MoreContract.State> {
        override fun create(state: MoreContract.State): MoreVM
    }

    companion object :
        MavericksViewModelFactory<MoreVM, MoreContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): MoreContract.State {
            return MoreContract.State(
                isLoading = false,
                items = emptyList()
            )
        }
    }
}