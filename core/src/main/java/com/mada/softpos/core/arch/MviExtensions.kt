package com.mada.softpos.core.arch

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import kotlin.reflect.KClass

fun MviScreen.addOnBackPressedCallback(
    owner: LifecycleOwner = viewLifecycleOwner,
    callback: () -> Unit,
) {
    try {
        requireActivity().onBackPressedDispatcher.addCallback(
            owner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() = callback.invoke()
            }
        )
    } catch (e: Exception) {
        Timber.e(this::class.java.simpleName, "Error setting back pressed callback")
    }
}

/**
 * Finish all activities immediately and start new-one
 * @param activityClass launched activity
 */
fun Activity.clearTaskAndStartActivity(activityClass: KClass<*>) {
    finishAffinity()
    val intent = Intent(this, activityClass.java)
    startActivity(intent)
}
