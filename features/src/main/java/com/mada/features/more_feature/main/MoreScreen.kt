package com.mada.features.more_feature.main

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.features.databinding.ScreenMoreBinding
import com.mada.softpos.core.arch.MviScreen
import com.mada.softpos.core.arch.withEffect
import com.mada.softpos.core.ui.bindToViewLifecycle
import com.mada.softpos.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreScreen : MviScreen() {

    override val binding: ScreenMoreBinding by viewBinding()
    private val viewModel: MoreVM by fragmentViewModel()

    private val choreograph by bindToViewLifecycle {
        MoreChoreograph(
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

    private fun handleViewActions(action: MoreChoreograph.ViewAction) {
        when (action) {
            is MoreChoreograph.ViewAction.OnItemClicked -> {
                viewModel.setEvent(MoreContract.Event.OnItemClicked(action.data))
            }
        }
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is MoreContract.Effect.ShowError -> showError(effect.error)
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            choreograph.invalidate(state)
        }
    }
}