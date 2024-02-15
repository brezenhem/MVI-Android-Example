package com.mada.softpos.core.ui.adapter.items

import com.mada.softpos.core.ui.adapter.base.BaseItem

sealed interface Spacer : BaseItem {
    object Spacer8dp : Spacer
    object Spacer16dp : Spacer
    object Spacer24dp : Spacer
    object Spacer32dp : Spacer
    object Spacer40dp : Spacer
    object Spacer48dp : Spacer
    object Spacer56dp : Spacer
}