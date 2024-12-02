package com.cocoslime.presentation.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.data.paging.GithubRepoPagingSource
import com.cocoslime.data.service.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor (
    private val githubService: GithubService,
): ViewModel() {
    private val _user = MutableStateFlow("cocoslime")
    val user: StateFlow<String> = _user

    @OptIn(ExperimentalCoroutinesApi::class)
    val repoPagingFlow: Flow<PagingData<UiModel>> = user.flatMapLatest {
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
        .map { pagingData ->
            pagingData
                .map {
                    UiModel.UserModel.fromDataModel(
                        it
                    )
                }
                .insertSeparators<UiModel.UserModel, UiModel> { before, after ->
                    when {
                        before == null -> UiModel.SeparatorModel("HEADER")
                        after == null -> UiModel.SeparatorModel("FOOTER")
                        // Return null to avoid adding a separator between two items.
                        else -> null
                    }
                }
        }
        .cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 10
    }

    sealed interface UiModel {
        data class UserModel(
            val id: Long,
            val name: String,
            val url: String,
            val description: String?,
            val language: String?,
        ): UiModel {
            companion object {
                fun fromDataModel(
                    data: GithubRepoResponse
                ) = UserModel(
                    id = data.id,
                    name = data.name,
                    url = data.url,
                    description = data.description,
                    language = data.language,
                )
            }
        }

        data class SeparatorModel(
            val description: String
        ): UiModel
    }
}