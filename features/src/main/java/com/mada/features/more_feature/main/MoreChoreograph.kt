package com.mada.features.more_feature.main

import com.mada.domain.load_more.model.LoadMoreModel
import com.mada.features.common.adapter.MenuItemAdapter
import com.mada.features.databinding.ScreenMoreBinding
import com.mada.softpos.core.R
import com.mada.softpos.core.arch.MviChoreograph
import com.mada.softpos.core.ui.adapter.ItemOffsetDecoration
import com.mada.softpos.core.ui.adapter.base.BaseItemDiffCallback
import com.mada.softpos.core.ui.adapter.base.delegate.CompositeDelegateAdapter

class MoreChoreograph(
    binding: ScreenMoreBinding,
    private val action: (ViewAction) -> Unit
) : MviChoreograph<MoreContract.State>() {

    private val context = binding.root.context

    private val adapter = CompositeDelegateAdapter.Builder.build(BaseItemDiffCallback()) {
        this += MenuItemAdapter(
            onItemClick = {
                action.invoke(ViewAction.OnItemClicked(LoadMoreModel.Type.LOGOUT))
                // handle other actions here
            }
        )
    }

    init {
        binding.apply {
            rvList.addItemDecoration(ItemOffsetDecoration(context, R.dimen._8dp))
            rvList.adapter = adapter
        }
    }

    override fun invalidate(state: MoreContract.State) {
        adapter.setItems(state.items)
    }


    sealed interface ViewAction {
        data class OnItemClicked(val data: LoadMoreModel.Type) : ViewAction
    }
}