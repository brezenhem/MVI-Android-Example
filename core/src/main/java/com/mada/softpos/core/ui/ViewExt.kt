package com.mada.softpos.core.ui

import android.view.View

fun View.setSafeOnClickListener(
    clickListener: (v: View) -> Unit,
) = setOnSafeClickListener(OnSafeClickListener(clickListener = clickListener))

fun View.setOnSafeClickListener(
    clickListener: OnSafeClickListener,
) = setOnClickListener(clickListener)
