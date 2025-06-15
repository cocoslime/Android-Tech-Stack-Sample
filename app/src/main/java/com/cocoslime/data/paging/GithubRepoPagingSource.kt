package com.cocoslime.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.data.service.GithubService
import com.cocoslime.common.logDebug

class GithubRepoPagingSource (
    private val service: GithubService,
    private val user: String,
    private val perPage: Int = 10
) : PagingSource<Int, GithubRepoResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, GithubRepoResponse> {
        try {
            params.printLog()

            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val range = nextPageNumber.until(nextPageNumber + params.loadSize / perPage)

            val response = range.map { key ->
                service.getRepos(
                    user,
                    perPage = perPage,
                    page = key
                )
            }
                .flatten()

            return LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = if (response.size != params.loadSize) null else range.last + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error for
            // expected errors (such as a network failure).
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepoResponse>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun <T: Any> LoadParams<T>.printLog() {
        "$this".logDebug(TAG)
        "loadSize: ${loadSize}".logDebug(TAG)
        "key: ${key}".logDebug(TAG)
    }

    companion object {
        private const val TAG = "GithubRepoPagingSource"
    }
}
