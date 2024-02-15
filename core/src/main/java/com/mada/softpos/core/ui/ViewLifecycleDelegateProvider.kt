package com.mada.softpos.core.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any> Fragment.bindToViewLifecycle(noinline provider: () -> T): LifecycleDelegate<T> {
    return LifecycleDelegate(
        this,
        provider
    )
}

class LifecycleDelegate<T : Any>(
    private val fragment: Fragment,
    private val provider: () -> T
) : ReadOnlyProperty<Fragment, T> {
    private var providerValue: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        providerValue?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}")
        }

        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    providerValue = null
                }
            })
        }

        providerValue = provider.invoke()

        return providerValue!!
    }
}

