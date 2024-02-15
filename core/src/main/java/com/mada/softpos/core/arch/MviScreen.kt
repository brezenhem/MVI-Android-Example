package com.mada.softpos.core.arch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.MavericksView
import com.google.android.material.snackbar.Snackbar
import com.mada.softpos.core.domain.ErrorResult
import kotlinx.coroutines.launch

abstract class MviScreen : Fragment(), MavericksView {

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

    open fun appearanceLightStatusBars(isLight: Boolean) {
        val window = requireActivity().window
        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = isLight
    }

    /**
     * Apply the system bars insect as a top margin of the view.
     *
     * @param v which should have a top margin from the status bar
     */
    @SuppressLint("WrongConstant")
    open fun applyStatusBarInsets(v: View) {
        ViewCompat.setOnApplyWindowInsetsListener(v) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            val param = view.layoutParams as ViewGroup.MarginLayoutParams
            param.topMargin = insets.top
            view.layoutParams = param
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * Method handles views witch should’t be visually obscured by the system bars.
     *
     * @param v root element in layout
     */
    fun applySystemBarInsets(v: View) {
        v.setOnApplyWindowInsetsListener { view, windowInsets ->
            val insetsTypesToFit =
                WindowInsets.Type.systemBars() and WindowInsets.Type.statusBars().inv()
            val insets = windowInsets.getInsets(insetsTypesToFit)
            view.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            windowInsets.inset(insets)
            view.onApplyWindowInsets(windowInsets)
        }
    }

    abstract fun handleEffects()

    open fun showError(error: ErrorResult) {
        val message = when (error) {
            is ErrorResult.ConnectionError -> "No internet connection"
            is ErrorResult.RuntimeError -> "Unknown error"
            is ErrorResult.ServerError -> error.errorMessage
        }
        Snackbar.make(binding.root, message, 2000).show()
    }
}

fun <VM : MviViewModel<Event, Effect, State>, Event : MviEvent, Effect : MviEffect, State : MviState, R> MviScreen.withEffect(
    viewModel: VM,
    block: (Effect) -> R,
) = lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.effectFlow.collect {
            block(it)
        }
    }
}