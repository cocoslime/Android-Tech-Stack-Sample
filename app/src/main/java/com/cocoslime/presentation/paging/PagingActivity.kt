package com.cocoslime.presentation.paging

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cocoslime.presentation.databinding.ActivityRecyclerViewBinding
import com.cocoslime.presentation.databinding.ItemRecyclerViewEntryBinding
import com.cocoslime.presentation.recyclerview.BindingViewHolder
import com.cocoslime.printlnDebug
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagingActivity : ComponentActivity() {

    private var _binding: ActivityRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PagingViewModel by viewModels()
    private val adapter: Adapter by lazy {
        Adapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
    }

    private fun initPager() {
        binding.recyclerView.apply {
            adapter = this@PagingActivity.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            viewModel.repoPagingFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val refresh = loadStates.refresh
                when (refresh) {
                    is LoadState.Loading -> {
                        Log.d("PagingActivity", refresh.toString())
                        // Show loading spinner
                    }
                    is LoadState.Error -> {
                        Log.e("PagingActivity", "Error", refresh.error)
                        Toast.makeText(
                            this@PagingActivity,
                            refresh.error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is LoadState.NotLoading -> {
                        Log.d("PagingActivity", refresh.toString())
                        // Hide loading spinner
                    }
                }
            }
        }
    }

    private class Adapter :
        PagingDataAdapter<PagingViewModel.UiModel, BindingViewHolder<out PagingViewModel.UiModel, *>>(
            UiModelDiffUtil()
        ) {

        override fun getItemViewType(position: Int): Int {
            return when (val item = getItem(position)) {
                is PagingViewModel.UiModel.UserModel -> 0
                is PagingViewModel.UiModel.SeparatorModel -> 1
                else -> throw IllegalArgumentException("Unknown view type: $item")
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BindingViewHolder<out PagingViewModel.UiModel, *> {
            return when(viewType) {
                0 -> RepoViewHolder(parent.context, parent)
                1 -> SeparatorViewHolder(parent)
                else -> throw IllegalArgumentException("Unknown view type: $viewType")
            }
        }

        override fun onBindViewHolder(holder: BindingViewHolder<out PagingViewModel.UiModel, *>, position: Int) {
            getItem(position)?.let { item ->
                holder.onBindItem(item)
            }
        }

        class SeparatorViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<PagingViewModel.UiModel.SeparatorModel, ItemRecyclerViewEntryBinding>(
            ItemRecyclerViewEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: PagingViewModel.UiModel.SeparatorModel) {
                binding.contents.text = item.description
            }
        }

        class RepoViewHolder(
            private val context: Context,
            parent: ViewGroup,
        ): BindingViewHolder<PagingViewModel.UiModel.UserModel, ItemRecyclerViewEntryBinding>(
            ItemRecyclerViewEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {

            override fun onBind(item: PagingViewModel.UiModel.UserModel) {
                Glide.with(binding.image)
                    .load(getLanguageImage(item.language) ?: run {
                        "Unknown language: ${item.language}".printlnDebug()
                        null
                    })
                    .into(binding.image)

                binding.contents.text = item.name
                binding.subContents.text = item.url

                binding.root.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                    if (context.packageManager.resolveActivity(intent, 0) != null) {
                        context.startActivity(intent)
                    } else {
                        "Cannot resolve activity ${item.url}".printlnDebug()
                    }
                }
            }

            private fun getLanguageImage(language: String?): String? {
                return when (language?.lowercase()) {
                    "kotlin" -> {
                        "https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin_Icon.png"
                    }
                    "javascript" -> {
                        "https://cdn.pixabay.com/photo/2015/04/23/17/41/javascript-736400_1280.png"
                    }
                    else -> null
                }
            }
        }

    }

    private class UiModelDiffUtil: DiffUtil.ItemCallback<PagingViewModel.UiModel>() {

        override fun areItemsTheSame(
            oldItem: PagingViewModel.UiModel,
            newItem: PagingViewModel.UiModel
        ): Boolean {
            return if (oldItem is PagingViewModel.UiModel.UserModel && newItem is PagingViewModel.UiModel.UserModel) {
                oldItem.id == newItem.id
            } else if (oldItem is PagingViewModel.UiModel.SeparatorModel && newItem is PagingViewModel.UiModel.SeparatorModel) {
                oldItem.description == newItem.description
            } else {
                false
            }
        }

        override fun areContentsTheSame(
            oldItem: PagingViewModel.UiModel,
            newItem: PagingViewModel.UiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}