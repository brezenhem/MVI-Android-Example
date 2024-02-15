package com.mada.features.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.internal.ThemeEnforcement
import com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap
import com.mada.features.R
import com.mada.features.databinding.ToolbarViewBinding

@SuppressLint("RestrictedApi")
class ToolbarView : MaterialToolbar {

    companion object {
        private val DEF_STYLE_RES = R.style.Widget_App_Toolbar
    }

    private val binding = ToolbarViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var title: CharSequence? = null
    private var titleColor: Int

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.ToolbarView
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr
    ) {
        val a = ThemeEnforcement.obtainStyledAttributes(
            context,
            attrs,
            R.styleable.ToolbarView,
            defStyleAttr,
            DEF_STYLE_RES
        )

        title = a.getString(R.styleable.ToolbarView_title)
        titleColor = a.getColor(R.styleable.ToolbarView_titleColor, Color.WHITE)

        a.recycle()

        setTitle(null)

        binding.apply {
            tvTitle.text = title
            tvTitle.setTextColor(titleColor)
        }
    }
}