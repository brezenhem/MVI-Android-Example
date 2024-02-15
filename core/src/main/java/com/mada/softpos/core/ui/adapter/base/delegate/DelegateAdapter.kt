package com.mada.softpos.core.ui.adapter.base.delegate

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

interface DelegateAdapter<VH : RecyclerView.ViewHolder, in T> {
    fun itemId(item: T): Long = RecyclerView.NO_ID
    fun onCreateViewHolder(parent: RecyclerView, viewType: Int, inflater: LayoutInflater): VH
    fun onBindViewHolder(holder: VH, item: T, payloads: List<Any>)
    fun onRecycled(holder: VH) = Unit
    fun isForViewType(item: T): Boolean
    fun onViewDetachedFromWindow(holder: VH) = Unit
    fun onViewAttachedToWindow(holder: VH) = Unit
}