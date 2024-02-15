package com.mada.features.history_feature.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mada.domain.load_transactions_history.model.TransHistoryModel
import com.mada.features.databinding.ItemTransHistoryBinding
import com.mada.softpos.core.extension.numberFormat

class TransHistoryAdapter :
    PagingDataAdapter<TransHistoryModel, TransHistoryAdapter.ItemViewHolder>(TransHistoryComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransHistoryBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class ItemViewHolder(private val binding: ItemTransHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: TransHistoryModel) {
            binding.tvId.text = "${item.id}"
            binding.tvDate.text = item.date
            binding.tvType.text = item.type
            binding.tvAmount.text = "$" + item.amount.numberFormat()
        }
    }
}

object TransHistoryComparator : DiffUtil.ItemCallback<TransHistoryModel>() {
    override fun areItemsTheSame(oldItem: TransHistoryModel, newItem: TransHistoryModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TransHistoryModel, newItem: TransHistoryModel) =
        oldItem == newItem
}