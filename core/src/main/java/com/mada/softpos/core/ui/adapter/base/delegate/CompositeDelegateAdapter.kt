package com.mada.softpos.core.ui.adapter.base.delegate

import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class CompositeDelegateAdapter<T : Any>(
    private val diffCallback: DiffUtil.ItemCallback<T>,
    typeToAdapterMap: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, T>>,
    private val _data: MutableList<T> = ArrayList(),
) : BaseCompositeDelegateAdapter<T>(typeToAdapterMap) {

    override val data: List<T> = _data

    private fun setItem(item: T, position: Int, payload: Any? = null) {
        _data[position] = item
        notifyItemChanged(position, payload)
    }

    fun addItem(item: T, position: Int = _data.size) {
        _data.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, count: Int = 1) {
        _data.removeAt(position)
        notifyItemRangeRemoved(position, count)
    }

    fun moveItem(from: Int, to: Int) {
        val tmp = _data[to]
        _data[to] = _data[from]
        _data[from] = tmp

        notifyItemMoved(from, to)
    }

    fun setItems(
        items: List<T>,
        detectMoves: Boolean = true,
    ) {
        val result =
            DiffUtil.calculateDiff(
                DiffCallbackAdapter(
                    diffCallback,
                    ArrayList(_data),
                    items
                ),
                detectMoves
            )

        _data.clear()
        _data.addAll(items)
        result.dispatchUpdatesTo(this)
    }

    fun addItems(items: Collection<T>, size: Int = _data.size) {
        val prevSize = _data.size

        _data.addAll(size, items)
        notifyItemRangeInserted(prevSize, items.size)
    }

    fun setItems(items: Collection<T>, offset: Int) {
        items.forEachIndexed { index, t -> _data[offset + index] = t }

        notifyItemRangeChanged(offset, items.size)
    }

    fun clear() {
        _data.clear()
        notifyDataSetChanged()
    }

    fun clearWithAnimating() {
        val size = _data.size

        _data.clear()
        notifyItemRangeRemoved(0, size)
    }

    operator fun set(position: Int, t: T) = setItem(t, position)

    operator fun plusAssign(t: T) = addItem(t)

    operator fun plusAssign(t: Collection<T>) = addItems(t)

    operator fun minusAssign(position: Int) = removeItem(position)

    class Builder<T : Any> private constructor(
        private val typeToAdapterMap: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, T>> = SparseArrayCompat(),
    ) {

        companion object {

            fun <T : Any> build(
                diffCallback: DiffUtil.ItemCallback<T>,
                data: MutableList<T> = ArrayList(),
                init: (Builder<T>.() -> Unit) = {},
            ): CompositeDelegateAdapter<T> {
                val builder = Builder<T>()

                builder.init()
                return builder.build(diffCallback, data)
            }

            fun <T : Any> build(
                diffCallback: DiffUtil.ItemCallback<T>,
                data: MutableList<T> = ArrayList(),
                adapters: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, T>>,
            ): CompositeDelegateAdapter<T> {
                return Builder(adapters).build(diffCallback, data)
            }
        }

        operator fun <V : RecyclerView.ViewHolder> plusAssign(delegateAdapter: DelegateAdapter<V, T>) {
            @Suppress("UNCHECKED_CAST")
            typeToAdapterMap.append(
                typeToAdapterMap.size(),
                delegateAdapter as DelegateAdapter<RecyclerView.ViewHolder, T>
            )
        }

        fun <V : RecyclerView.ViewHolder> add(delegateAdapter: DelegateAdapter<V, T>): Builder<T> {
            @Suppress("UNCHECKED_CAST")
            typeToAdapterMap.append(
                typeToAdapterMap.size(),
                delegateAdapter as DelegateAdapter<RecyclerView.ViewHolder, T>
            )

            return this
        }

        private fun build(
            diffCallback: DiffUtil.ItemCallback<T>,
            data: MutableList<T> = ArrayList(),
        ): CompositeDelegateAdapter<T> {
            return CompositeDelegateAdapter(diffCallback, typeToAdapterMap, data)
        }
    }
}