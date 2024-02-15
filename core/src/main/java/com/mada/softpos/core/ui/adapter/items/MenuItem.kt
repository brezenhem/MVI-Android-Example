package com.mada.softpos.core.ui.adapter.items

import com.mada.softpos.core.ui.adapter.base.BaseItem

data class MenuItem(
    val title: String,
    val resIcon: Int? = null
) : BaseItem