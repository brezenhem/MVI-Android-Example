package com.mada.features.auth.login

import android.content.Intent
import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.mada.features.main.MainActivity
import com.mada.features.databinding.ScreenLoginBinding
import com.mada.softpos.core.arch.MviScreen
import com.mada.softpos.core.arch.withEffect
import com.mada.softpos.core.ui.bindToViewLifecycle
import com.mada.softpos.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : MviScreen() {

    override val binding: ScreenLoginBinding by viewBinding()
    private val viewModel: LoginVM by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appearanceLightStatusBars(true)
    }

    private val choreograph by bindToViewLifecycle {
        LoginChoreograph(
            binding = binding,
            action = { action ->
                handleViewActions(action)
            }
        )
    }

    private fun handleViewActions(action: LoginChoreograph.ViewAction) {
        when (action) {
            is LoginChoreograph.ViewAction.OnLoginClicked -> {
                viewModel.setEvent(
                    LoginContract.Event.StartLogin(
                        action.username,
                        action.password
                    )
                )
            }
        }
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is LoginContract.Effect.ShowError -> showError(effect.error)
                is LoginContract.Effect.UserLoggedIn -> showMainActivity()
            }
        }
    }

    private fun showMainActivity() {
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            startActivity(intent)
            it.finish()
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            choreograph.invalidate(state)
        }
    }
}