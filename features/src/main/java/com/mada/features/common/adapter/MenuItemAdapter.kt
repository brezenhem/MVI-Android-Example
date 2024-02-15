package com.mada.features.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mada.softpos.core.ui.adapter.items.MenuItem
import com.mada.features.databinding.ItemMenuBinding
import com.mada.softpos.core.ui.adapter.base.BaseItem
import com.mada.softpos.core.ui.adapter.base.delegate.DataBoundDelegateAdapter
import com.mada.softpos.core.ui.setSafeOnClickListener

class MenuItemAdapter(
    private val onItemClick: () -> Unit
) : DataBoundDelegateAdapter<BaseItem, ItemMenuBinding>() {

    override fun createBinding(parent: ViewGroup): ItemMenuBinding {
        return ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun isForViewType(item: BaseItem): Boolean {
        return item is MenuItem
    }

    override fun bind(binding: ItemMenuBinding, item: BaseItem) {
        item as MenuItem

        binding.apply {
            bindData(
                text = item.title,
                resIcon = item.resIcon
            )

            root.setSafeOnClickListener { onItemClick() }
        }
    }

    private fun ItemMenuBinding.bindData(text: CharSequence?, resIcon: Int?) {
        title.text = text
        resIcon?.let {
            icon.setImageResource(resIcon)
        }
    }
}