package com.cocoslime.samples.apps.androidtechstack.presentation.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingViewHolder<ItemType, VB: ViewBinding> (
    val binding: VB,
): RecyclerView.ViewHolder(binding.root) {
    @Suppress("UNCHECKED_CAST")
    fun onBindItem(item: Any) {
        onBind(item as ItemType)
    }

    abstract fun onBind(item: ItemType)
}
