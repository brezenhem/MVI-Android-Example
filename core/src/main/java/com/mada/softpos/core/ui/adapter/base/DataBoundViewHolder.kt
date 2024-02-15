package com.mada.softpos.core.ui.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class DataBoundViewHolder<out T : ViewBinding> constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root)