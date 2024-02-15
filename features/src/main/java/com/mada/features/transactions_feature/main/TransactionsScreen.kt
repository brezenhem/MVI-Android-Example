package com.mada.features.transactions_feature.main

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.mada.features.databinding.ScreenTransactionsBinding
import com.mada.softpos.core.arch.MviScreen
import com.mada.softpos.core.arch.withEffect
import com.mada.softpos.core.ui.bindToViewLifecycle
import com.mada.softpos.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsScreen : MviScreen() {

    override val binding: ScreenTransactionsBinding by viewBinding()
    private val viewModel: TransactionsVM by fragmentViewModel()

    private val choreograph by bindToViewLifecycle {
        TransactionsChoreograph(
            binding = binding,
            action = { action ->
                handleViewActions(action)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyStatusBarInsets(binding.toolbarView)
    }

    private fun handleViewActions(action: TransactionsChoreograph.ViewAction) {
        when (action) {
            TransactionsChoreograph.ViewAction.OnItemClicked -> {
                viewModel.setEvent(TransactionsContract.Event.OnItemClicked)
            }
        }
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is TransactionsContract.Effect.ShowError -> showError(effect.error)
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            choreograph.invalidate(state)
        }
    }
}