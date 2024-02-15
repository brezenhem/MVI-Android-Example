package com.mada.features.main

import android.app.Activity
import android.content.Intent
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.mada.domain.user_logged_in.UserLoggedInUseCase
import com.mada.domain.user_session.SessionLifecycleUseCase
import com.mada.features.auth.main.AuthActivity
import com.mada.softpos.core.arch.MviViewModel
import com.mada.softpos.core.di.AssistedViewModelFactory
import com.mada.softpos.core.di.hiltMavericksViewModelFactory
import com.mada.softpos.core.domain.CallableResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainVM @AssistedInject constructor(
    @Assisted var state: MainContract.State,
    private var userLoggedInUseCase: UserLoggedInUseCase,
    private var sessionLifecycleUseCase: SessionLifecycleUseCase
) : MviViewModel<MainContract.Event, MainContract.Effect, MainContract.State>(
    state
) {

    init {
        subscribeSessionLifecycle()
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnKeepOnScreenCondition -> setKeepOnScreenCondition(event.value)
            is MainContract.Event.OnCheckUserSignedIn -> checkUserSignedIn(event.activity)
        }
    }

    private fun subscribeSessionLifecycle() {
        viewModelScope.launch {
            sessionLifecycleUseCase.execute(Unit)
                .collectLatest {
                    setEffect { MainContract.Effect.Logout }
                }
        }
    }

    private fun checkUserSignedIn(activity: Activity) {
        when (val result = userLoggedInUseCase.execute(Unit)) {
            is CallableResult.Success -> {
                if (!result.data) {
                    startIntent(Intent(activity, AuthActivity::class.java))
                } else {
                    setKeepOnScreenCondition(false)
                }
            }

            is CallableResult.Failure -> {}
        }
    }

    private fun startIntent(intent: Intent) {
        setEffect { MainContract.Effect.StartIntent(intent) }
    }

    private fun setKeepOnScreenCondition(value: Boolean) {
        setEffect { MainContract.Effect.SetKeepOnScreenCondition(value) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainVM, MainContract.State> {
        override fun create(state: MainContract.State): MainVM
    }

    companion object :
        MavericksViewModelFactory<MainVM, MainContract.State> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): MainContract.State {
            return MainContract.State(
                isLoading = false,
            )
        }
    }
}
