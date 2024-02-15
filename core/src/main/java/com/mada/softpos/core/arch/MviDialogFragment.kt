package com.mada.softpos.core.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.MavericksView
import kotlinx.coroutines.launch

abstract class MviDialogFragment : DialogFragment(), MavericksView {

    protected abstract val binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEffects()
    }

    abstract fun handleEffects()
}

fun <VM : MviViewModel<Event, Effect, State>, Event : MviEvent, Effect : MviEffect, State : MviState, R> MviDialogFragment.withEffect(
    viewModel: VM,
    block: (Effect) -> R,
) = lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.effectFlow.collect {
            block(it)
        }
    }
}