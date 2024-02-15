package com.mada.features.history_feature.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.mada.features.databinding.ScreenHistoryBinding
import com.mada.softpos.core.arch.MviScreen
import com.mada.softpos.core.arch.withEffect
import com.mada.softpos.core.ui.bindToViewLifecycle
import com.mada.softpos.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryScreen : MviScreen() {

    override val binding: ScreenHistoryBinding by viewBinding()
    private val viewModel: HistoryVM by fragmentViewModel()

    private val choreograph by bindToViewLifecycle {
        HistoryChoreograph(
            binding = binding,
            lifecycleScope = lifecycleScope
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEvent(HistoryContract.Event.OnLoadTitle)
        appearanceLightStatusBars(true)
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is HistoryContract.Effect.ShowError -> showError(effect.error)
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            choreograph.invalidate(state)
        }
    }
}