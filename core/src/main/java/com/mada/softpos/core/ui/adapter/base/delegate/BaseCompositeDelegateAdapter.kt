package com.mada.softpos.core.ui.adapter.base.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

abstract class BaseCompositeDelegateAdapter<T>(
    private val typeToAdapterMap: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, T>>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FIRST_VIEW_TYPE = 0
    }

    abstract val data: List<T>

    open fun get(i: Int) = data[i]

    final override fun getItemViewType(position: Int): Int {
        val item = data[position]
        for (i in FIRST_VIEW_TYPE until typeToAdapterMap.size()) {
            val delegate = typeToAdapterMap.valueAt(i)
            if (delegate.isForViewType(item)) {
                return typeToAdapterMap.keyAt(i)
            }
        }

        throw NullPointerException("Can`t get viewType for position $position")
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        requireNotNull(typeToAdapterMap.get(viewType)) { "Couldn`t find adapter for viewType $viewType" }
            .let { adapter ->
                return adapter.onCreateViewHolder(
                    parent as RecyclerView,
                    viewType,
                    LayoutInflater.from(parent.context)
                )
            }
    }

    final override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        onBindViewHolder(holder, position, emptyList())
    }

    final override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        requireNotNull(typeToAdapterMap.get(getItemViewType(position))) { "Couldn`t find adapter for position $position" }
            .apply { onBindViewHolder(holder, data[position], payloads) }
    }

    final override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        requireNotNull(typeToAdapterMap.get(holder.itemViewType)) { "Couldn`t find adapter for viewType ${holder.itemViewType}" }
            .apply { onViewDetachedFromWindow(holder) }
    }

    final override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        requireNotNull(typeToAdapterMap.get(holder.itemViewType)) { "Couldn`t find adapter for viewType ${holder.itemViewType}" }
            .apply { onViewAttachedToWindow(holder) }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        requireNotNull(typeToAdapterMap.get(holder.itemViewType)) { "Couldn`t find adapter for viewType ${holder.itemViewType}" }
            .apply { onRecycled(holder) }
    }

    final override fun getItemCount() = data.size

    final override fun getItemId(position: Int): Long {
        requireNotNull(typeToAdapterMap.get(getItemViewType(position))) { "Couldn`t find adapter for position $position" }
            .let { adapter ->
                return adapter.itemId(data[position])
            }
    }
}