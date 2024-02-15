package com.mada.softpos.core.ui.adapter.base.delegate

import androidx.recyclerview.widget.DiffUtil

class DiffCallbackAdapter<in T : Any>(
    private val delegate: DiffUtil.ItemCallback<T>,
    private val old: List<T>,
    private val new: List<T>,
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        delegate.areItemsTheSame(old[oldItemPosition], new[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        delegate.areContentsTheSame(old[oldItemPosition], new[newItemPosition])

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
        delegate.getChangePayload(old[oldItemPosition], new[newItemPosition])
}