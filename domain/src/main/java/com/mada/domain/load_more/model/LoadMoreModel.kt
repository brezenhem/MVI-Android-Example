package com.mada.domain.load_more.model

import com.mada.softpos.core.ui.adapter.base.BaseItem

data class LoadMoreModel(
    val items: List<BaseItem>
) {
    enum class Type {
        PROFILE, SETTINGS, DEBUG, BUILD_INFO, LOGOUT
    }
}