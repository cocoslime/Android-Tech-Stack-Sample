package com.cocoslime.presentation.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cocoslime.data.paging.GithubRepoPagingSource
import com.cocoslime.data.service.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor (
    private val githubService: GithubService,
): ViewModel() {
    private val _user = MutableStateFlow("cocoslime")
    val user: StateFlow<String> = _user

    @OptIn(ExperimentalCoroutinesApi::class)
    val repoPagingFlow = user.flatMapLatest {
        Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE * 2,
            )
        ) {
            GithubRepoPagingSource(
                githubService,
                it,
                perPage = PAGE_SIZE
            )
        }
            .flow
    }
        .cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 10
    }
}