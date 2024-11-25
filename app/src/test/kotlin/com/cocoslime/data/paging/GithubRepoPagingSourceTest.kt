package com.cocoslime.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.cocoslime.common.createFakeGithubRepoResponse
import com.cocoslime.presentation.data.service.TestGithubService
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GithubRepoPagingSourceTest {

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10
        )
        val mockRepoResponseList = (0 until 10).map { createFakeGithubRepoResponse(it.toLong()) }
        val pagingSource = GithubRepoPagingSource(
            service = TestGithubService().apply {
                repoResponseList.addAll(mockRepoResponseList)
            },
            user = "cocoslime",
            perPage = 10
        )

        val pager = TestPager(config, pagingSource)
        val result = pager.refresh()

        when (result) {
            is PagingSource.LoadResult.Page -> {
                assertEquals(result.data, mockRepoResponseList)
            }
            is PagingSource.LoadResult.Error -> {
                throw IllegalStateException("Expected successful result, not error\n$result")
            }
            is PagingSource.LoadResult.Invalid -> {
                throw IllegalStateException("Expected successful result, not invalid\n$result")
            }
        }
    }
}