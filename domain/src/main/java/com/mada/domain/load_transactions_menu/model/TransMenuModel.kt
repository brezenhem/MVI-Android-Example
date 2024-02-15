package com.mada.domain.load_transactions_menu.model

import com.mada.softpos.core.ui.adapter.base.BaseItem

data class TransMenuModel(
    private val title: String,
    private val resIcon: Int
) : BaseItem