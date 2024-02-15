package com.mada.softpos.core.ui.adapter.base

import androidx.recyclerview.widget.DiffUtil

interface BaseItem

interface DataItem {
    val data: Any?
}

class BaseItemDiffCallback : DiffUtil.ItemCallback<BaseItem>() {

    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.javaClass == newItem.javaClass
    }

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
        return oldItem.equals(newItem)
    }
}