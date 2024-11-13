package com.cocoslime.presentation.paging

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
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.presentation.databinding.ActivityRecyclerViewBinding
import com.cocoslime.presentation.databinding.ItemRecyclerViewEntryBinding
import com.cocoslime.presentation.recyclerview.BindingViewHolder
import com.cocoslime.presentation.recyclerview.ListItem
import com.cocoslime.presentation.recyclerview.ListItemCallback
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
                        println("refresh.endOfPaginationReached ${refresh.endOfPaginationReached}")
                        // Hide loading spinner
                    }
                }
            }
        }
    }

    private class Adapter :
        PagingDataAdapter<GithubRepoResponse, BindingViewHolder<out GithubRepoResponse, *>>(
            GithubRepoResponseDiffUtil()
        ) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RepoViewHolder {
            return RepoViewHolder(parent)
        }

        override fun onBindViewHolder(holder: BindingViewHolder<out GithubRepoResponse, *>, position: Int) {
            val item = getItem(position)
            if (item != null) {
                holder.onBindItem(item)
            }
        }

        class RepoViewHolder(
            parent: ViewGroup,
        ): BindingViewHolder<GithubRepoResponse, ItemRecyclerViewEntryBinding>(
            ItemRecyclerViewEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            override fun onBind(item: GithubRepoResponse) {
                binding.contents.text = item.name + "\n" + item.description.orEmpty()
            }
        }

    }

    private class GithubRepoResponseDiffUtil: DiffUtil.ItemCallback<GithubRepoResponse>() {
        override fun areItemsTheSame(
            oldItem: GithubRepoResponse,
            newItem: GithubRepoResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GithubRepoResponse,
            newItem: GithubRepoResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}