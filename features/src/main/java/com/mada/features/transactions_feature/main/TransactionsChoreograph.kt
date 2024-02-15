package com.mada.features.transactions_feature.main

import com.mada.features.common.adapter.MenuItemAdapter
import com.mada.features.databinding.ScreenTransactionsBinding
import com.mada.softpos.core.R
import com.mada.softpos.core.arch.MviChoreograph
import com.mada.softpos.core.ui.adapter.ItemOffsetDecoration
import com.mada.softpos.core.ui.adapter.base.BaseItemDiffCallback
import com.mada.softpos.core.ui.adapter.base.delegate.CompositeDelegateAdapter

class TransactionsChoreograph(
    binding: ScreenTransactionsBinding,
    private val action: (ViewAction) -> Unit
) : MviChoreograph<TransactionsContract.State>() {

    private val context = binding.root.context

    private val adapter = CompositeDelegateAdapter.Builder.build(BaseItemDiffCallback()) {
        this += MenuItemAdapter(
            onItemClick = {
                action.invoke(ViewAction.OnItemClicked)
            }
        )
    }

    init {
        binding.apply {
            rvList.addItemDecoration(ItemOffsetDecoration(context, R.dimen._8dp))
            rvList.adapter = adapter
        }
    }

    override fun invalidate(state: TransactionsContract.State) {
        adapter.setItems(state.items)
    }

    sealed interface ViewAction {
        object OnItemClicked : ViewAction
    }
}