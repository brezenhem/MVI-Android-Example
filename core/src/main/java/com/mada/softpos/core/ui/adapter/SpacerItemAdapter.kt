package com.mada.softpos.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mada.softpos.core.R
import com.mada.softpos.core.databinding.ItemSpacerBinding
import com.mada.softpos.core.ui.adapter.base.BaseItem
import com.mada.softpos.core.ui.adapter.base.delegate.DataBoundDelegateAdapter
import com.mada.softpos.core.ui.adapter.items.Spacer

class SpacerItemAdapter : DataBoundDelegateAdapter<BaseItem, ItemSpacerBinding>() {

    override fun createBinding(parent: ViewGroup): ItemSpacerBinding {
        return ItemSpacerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun isForViewType(item: BaseItem): Boolean {
        return item is Spacer
    }

    override fun bind(binding: ItemSpacerBinding, item: BaseItem) {
        val layoutParams = binding.root.layoutParams
        layoutParams.height = binding.root.resources.getDimension(
            when (item) {
                is Spacer.Spacer8dp -> R.dimen._8dp
                is Spacer.Spacer16dp -> R.dimen._16dp
                is Spacer.Spacer24dp -> R.dimen._24dp
                is Spacer.Spacer32dp -> R.dimen._32dp
                is Spacer.Spacer40dp -> R.dimen._40dp
                is Spacer.Spacer48dp -> R.dimen._48dp
                is Spacer.Spacer56dp -> R.dimen._56dp
                else -> 0
            }
        ).toInt()
    }
}