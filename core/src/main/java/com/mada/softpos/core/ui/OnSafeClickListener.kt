package com.mada.softpos.core.ui

import android.view.View

class OnSafeClickListener(
    private var intervalInMs: Long = DEFAULT_DELAY_BETWEEN_CLICKS,
    private val clickListener: (v: View) -> Unit,
) : View.OnClickListener {

    companion object {
        private const val DEFAULT_DELAY_BETWEEN_CLICKS = 500L
    }

    override fun onClick(v: View?) {
        v?.run {
            clickListener.invoke(this)
            disableClickTemporary(intervalInMs)
        }
    }

    private fun View.disableClickTemporary(intervalInMs: Long) {
        isClickable = false
        postDelayed({ isClickable = true }, intervalInMs)
    }
}

