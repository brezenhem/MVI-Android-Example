package com.mada.features.auth.login

import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.login_user.UserLoginUseCase
import com.mada.domain.login_user.dto.LoginDto
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

class LoginVM @AssistedInject constructor(
    @Assisted var state: LoginContract.State,
    private val userLoginUseCase: UserLoginUseCase
) : MviViewModel<LoginContract.Event, LoginContract.Effect, LoginContract.State>(
    state
) {

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.StartLogin -> login(event.username, event.password)
        }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            userLoginUseCase(LoginDto(username, password))
                .collectLatest { result ->
                    when (result) {
                        is SuspendResult.Loading -> {
                            setState { copy(isLoading = true) }
                        }

                        is SuspendResult.Success -> {
                            setState { copy(isLoading = false) }
                            setEffect { LoginContract.Effect.UserLoggedIn }
                        }

                        is SuspendResult.Failure -> {
                            setState { copy(isLoading = false) }
                            setEffect { LoginContract.Effect.ShowError(result.exception.asResult()) }
                        }
                    }
                }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<LoginVM, LoginContract.State> {
        override fun create(state: LoginContract.State): LoginVM
    }

    companion object :
        MavericksViewModelFactory<LoginVM, LoginContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): LoginContract.State {
            return LoginContract.State(
                isLoading = false
            )
        }
    }
}