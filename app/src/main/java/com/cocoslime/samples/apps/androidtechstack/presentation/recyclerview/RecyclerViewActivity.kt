package com.cocoslime.samples.apps.androidtechstack.presentation.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.cocoslime.samples.apps.androidtechstack.presentation.common.list.CommonListItemContainer
import com.cocoslime.samples.apps.androidtechstack.presentation.common.list.ListItemCallback
import com.cocoslime.samples.apps.androidtechstack.presentation.common.list.dummyListItems
import com.cocoslime.samples.apps.androidtechstack.databinding.ActivityRecyclerViewBinding
import com.cocoslime.samples.apps.androidtechstack.databinding.ItemRecyclerViewEntryBinding
import com.cocoslime.samples.apps.androidtechstack.databinding.ItemRecyclerViewFooterBinding
import com.cocoslime.samples.apps.androidtechstack.databinding.ItemRecyclerViewHeaderBinding
import kotlinx.coroutines.launch

class RecyclerViewActivity: ComponentActivity() {

    private var _binding: ActivityRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private val listItemAdapter: Adapter by lazy {
        Adapter(
            onClickEntry = { entry ->
                Log.d(
                    "RecyclerViewActivity",
                    "Clicked: ${entry.content}"
                )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {

        binding.recyclerView.apply {
            adapter = listItemAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)

            Adapter.ViewType.entries.forEach {
                recycledViewPool.setMaxRecycledViews(it.ordinal, it.poolSize)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listItemAdapter.submitList(
                    dummyListItems
                )
            }
        }
    }

    private class Adapter(
        val onClickEntry: (CommonListItemContainer.Entry) -> Unit,
    ): ListAdapter<CommonListItemContainer, BindingViewHolder<out CommonListItemContainer, *>>(
        ListItemCallback()
    ) {

        override fun getItemViewType(position: Int): Int {
            return when (getItem(position)) {
                is CommonListItemContainer.Header -> ViewType.HEADER.ordinal
                is CommonListItemContainer.Entry -> ViewType.ENTRY.ordinal
                is CommonListItemContainer.Footer -> ViewType.FOOTER.ordinal
                else -> throw NotImplementedError()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<out CommonListItemContainer, *> {
            return when(viewType) {
                ViewType.HEADER.ordinal -> HeaderViewHolder(parent)
                ViewType.ENTRY.ordinal -> EntryViewHolder(parent, onClickEntry)
                ViewType.FOOTER.ordinal -> FooterViewHolder(parent)
                else -> throw NotImplementedError()
            }
        }

        override fun onBindViewHolder(holder: BindingViewHolder<out CommonListItemContainer, *>, position: Int) {
            val item = getItem(position)
            holder.onBindItem(item)
        }

        private class HeaderViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<CommonListItemContainer.Header, ItemRecyclerViewHeaderBinding>(
            binding = ItemRecyclerViewHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: CommonListItemContainer.Header) = Unit
        }

        private class EntryViewHolder(
            parent: ViewGroup,
            private val onClickEntry: (CommonListItemContainer.Entry) -> Unit,
        ): BindingViewHolder<CommonListItemContainer.Entry, ItemRecyclerViewEntryBinding>(
            binding = ItemRecyclerViewEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: CommonListItemContainer.Entry) {
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .into(binding.image)

                binding.contents.text = item.content

                binding.root.setOnClickListener {
                    onClickEntry(item)
                }
            }
        }

        private class FooterViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<CommonListItemContainer.Footer, ItemRecyclerViewFooterBinding>(
            binding = ItemRecyclerViewFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: CommonListItemContainer.Footer) {
                binding.message.text = item.message
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
