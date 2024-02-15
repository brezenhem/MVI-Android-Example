package com.mada.features.management_feature.main

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.mada.features.databinding.ScreenManagementBinding
import com.mada.softpos.core.arch.MviScreen
import com.mada.softpos.core.arch.withEffect
import com.mada.softpos.core.ui.bindToViewLifecycle
import com.mada.softpos.core.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementScreen : MviScreen() {

    override val binding: ScreenManagementBinding by viewBinding()
    private val viewModel: ManagementVM by fragmentViewModel()

    private val choreograph by bindToViewLifecycle {
        ManagementChoreograph(
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

    private fun handleViewActions(action: ManagementChoreograph.ViewAction) {
        when (action) {
            ManagementChoreograph.ViewAction.OnItemClicked -> {
                viewModel.setEvent(ManagementContract.Event.OnItemClicked)
            }
        }
    }

    override fun handleEffects() {
        withEffect(viewModel) { effect ->
            when (effect) {
                is ManagementContract.Effect.ShowError -> showError(effect.error)
            }
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            choreograph.invalidate(state)
        }
    }
}