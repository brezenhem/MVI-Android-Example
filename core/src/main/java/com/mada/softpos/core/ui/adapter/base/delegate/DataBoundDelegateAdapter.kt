package com.mada.softpos.core.ui.adapter.base.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mada.softpos.core.ui.adapter.base.DataBoundViewHolder

abstract class DataBoundDelegateAdapter<T, V : ViewBinding> :
    DelegateAdapter<DataBoundViewHolder<V>, T> {

    override fun onCreateViewHolder(
        parent: RecyclerView,
        viewType: Int,
        inflater: LayoutInflater,
    ): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, item: T, payloads: List<Any>) {
        bind(holder.binding, item)
    }

    protected abstract fun bind(binding: V, item: T)
}