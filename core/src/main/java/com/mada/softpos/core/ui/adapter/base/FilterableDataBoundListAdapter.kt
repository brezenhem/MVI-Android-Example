package com.mada.softpos.core.ui.adapter.base

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import java.util.concurrent.Executors

abstract class FilterableDataBoundListAdapter<T, V : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
), Filterable {

    private var originalList: List<T> = currentList.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
    }

    protected abstract fun bind(binding: V, item: T)

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (constraint.isNullOrEmpty()) {
                        originalList
                    } else {
                        onFilter(originalList, constraint.toString())
                    }
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<T>, true)
            }
        }
    }

    override fun submitList(list: List<T>?) {
        submitList(list, false)
    }

    protected abstract fun onFilter(list: List<T>, constraint: String): List<T>

    private fun submitList(list: List<T>?, filtered: Boolean) {
        if (!filtered) {
            originalList = list ?: listOf()
        }

        super.submitList(list)
    }
}