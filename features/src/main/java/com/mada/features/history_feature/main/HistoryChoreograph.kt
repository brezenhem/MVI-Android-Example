package com.mada.features.history_feature.main

import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.color
import androidx.lifecycle.LifecycleCoroutineScope
import com.mada.features.databinding.ScreenHistoryBinding
import com.mada.softpos.core.R
import com.mada.softpos.core.arch.MviChoreograph
import com.mada.softpos.core.ui.adapter.ItemOffsetDecoration
import com.mada.softpos.core.ui.paging.PagingLoadStateAdapter
import kotlinx.coroutines.launch

class HistoryChoreograph(
    val binding: ScreenHistoryBinding,
    val lifecycleScope: LifecycleCoroutineScope,
) : MviChoreograph<HistoryContract.State>() {

    private val context = binding.root.context

    private var adapter: TransHistoryAdapter

    init {
        binding.apply {
            adapter = TransHistoryAdapter()
            rvList.addItemDecoration(ItemOffsetDecoration(context, R.dimen._6dp))
            rlSwipe.setOnRefreshListener { adapter.refresh() }
            rvList.setHasFixedSize(true)
            rvList.itemAnimator = null
            rvList.adapter =
                adapter.withLoadStateFooter(footer = PagingLoadStateAdapter { adapter.retry() })
        }
        showTotalAmount("Last Day")
    }

    private fun showTotalAmount(period: String) {
        val totalAmountText = SpannableStringBuilder()
            .color(ContextCompat.getColor(context, R.color.white_90)
            ) {
                append(context.getString(com.mada.features.R.string.transactions_total))
            }
            .append(" ")
            .color(ContextCompat.getColor(context, R.color.white)) {
                bold { append(period) }
            }
        binding.header.totalAmount.text = totalAmountText
    }

    override fun invalidate(state: HistoryContract.State) {
        lifecycleScope.launch {
            adapter.submitData(state.items)
            binding.rlSwipe.isRefreshing = false
        }
    }
}