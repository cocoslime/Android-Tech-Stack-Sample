package com.cocoslime.presentation.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cocoslime.presentation.databinding.ActivityRecyclerViewBinding
import com.cocoslime.presentation.databinding.ItemRecyclerViewEntryBinding
import com.cocoslime.presentation.databinding.ItemRecyclerViewHeaderBinding

class RecyclerViewActivity: ComponentActivity() {

    private var _binding: ActivityRecyclerViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {

    }

    private class Adapter(

    ): ListAdapter<ListItem, BindingViewHolder<out ListItem, *>>(
        ListItemCallback()
    ) {

        override fun getItemViewType(position: Int): Int {
            return when (getItem(position)) {
                is ListItem.Header -> ViewType.HEADER.ordinal
                is ListItem.Entry -> ViewType.ENTRY.ordinal
                is ListItem.Footer -> ViewType.FOOTER.ordinal
                else -> throw NotImplementedError()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<out ListItem, *> {
            return when(viewType) {
                ViewType.HEADER.ordinal -> HeaderViewHolder(parent)
                ViewType.ENTRY.ordinal -> EntryViewHolder(parent)
                else -> throw NotImplementedError()
            }
        }

        override fun onBindViewHolder(holder: BindingViewHolder<out ListItem, *>, position: Int) {
            val item = getItem(position)
            holder.onBind(item)
        }

        private class HeaderViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<ListItem.Header, ItemRecyclerViewHeaderBinding>(
            binding = ItemRecyclerViewHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: ListItem.Header) = Unit
        }

        private class EntryViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<ListItem.Entry, ItemRecyclerViewEntryBinding>(
            binding = ItemRecyclerViewEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: ListItem.Entry) {
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .into(binding.image)

                binding.contents.text = item.content
            }
        }

        enum class ViewType(
            val poolSize: Int = 5
        ) {
            HEADER(1),
            ENTRY,
            FOOTER(1),
        }
    }
}