package com.mada.features.auth.login

import android.view.View
import com.mada.features.databinding.ScreenLoginBinding
import com.mada.softpos.core.arch.MviChoreograph
import com.mada.softpos.core.ui.setSafeOnClickListener

class LoginChoreograph(
    val binding: ScreenLoginBinding,
    private val action: (ViewAction) -> Unit
) : MviChoreograph<LoginContract.State>() {

    init {
        binding.apply {
            btnLogin.setSafeOnClickListener {
                action.invoke(
                    ViewAction.OnLoginClicked(
                        etEmail.editableText.toString(),
                        etPassword.editableText.toString()
                    )
                )
            }
        }
    }

    override fun invalidate(state: LoginContract.State) {
        binding.progress.root.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    sealed interface ViewAction {
        data class OnLoginClicked(
            val username: String,
            val password: String
        ) : ViewAction
    }
}