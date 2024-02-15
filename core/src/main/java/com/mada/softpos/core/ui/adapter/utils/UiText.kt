package com.mada.softpos.core.ui.adapter.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class Plain(val value: CharSequence) : UiText()

    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String = asCharSequence(context).toString()

    fun asCharSequence(context: Context): CharSequence {
        return when (this) {
            is Plain -> value
            is StringResource -> {
                if (args.isEmpty()) {
                    context.getText(resId)
                } else {
                    context.getString(resId, *args.prepareArgs(context))
                }
            }
        }
    }

    private fun List<Any>.prepareArgs(context: Context): Array<Any> {
        return map {
            when (it) {
                is UiText -> it.asCharSequence(context)
                else -> it
            }
        }.toTypedArray()
    }
}

fun CharSequence?.toUiText(): UiText = UiText.Plain(value = this ?: "")

fun @receiver:StringRes Int.toUiText(
    args: List<Any> = emptyList()
): UiText = UiText.StringResource(
    resId = this,
    args = args
)

fun emptyUiText(): UiText = UiText.Plain(value = "")

fun UiText.isEmptyUiText(): Boolean = this is UiText.Plain && this.value.isEmpty()