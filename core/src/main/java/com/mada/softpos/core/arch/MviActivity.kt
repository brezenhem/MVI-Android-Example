package com.mada.softpos.core.arch

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.mvrx.MavericksView
import kotlinx.coroutines.launch

abstract class MviActivity : AbstractActivity(), MavericksView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleEffects()
    }

    abstract fun handleEffects()
}

fun <VM : MviViewModel<Event, Effect, State>, Event : MviEvent, Effect : MviEffect, State : MviState, R> MviActivity.withEffect(
    viewModel: VM,
    block: (Effect) -> R,
) = lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.effectFlow.collect {
            block(it)
        }
    }
}
