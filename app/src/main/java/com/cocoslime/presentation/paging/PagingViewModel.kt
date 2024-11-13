package com.cocoslime.presentation.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.cocoslime.data.paging.GithubRepoPagingSource
import com.cocoslime.data.service.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor (
    private val githubService: GithubService,
): ViewModel() {
    private val _user = MutableStateFlow("cocoslime")
    val user: StateFlow<String> = _user

    val repoPagingFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 10)
    ) {
        GithubRepoPagingSource(githubService, user.value)
    }
        .flow
        .cachedIn(viewModelScope)
}