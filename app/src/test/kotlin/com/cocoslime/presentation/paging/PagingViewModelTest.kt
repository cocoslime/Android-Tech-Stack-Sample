package com.cocoslime.presentation.paging


import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.cocoslime.common.createFakeGithubRepoResponse
import com.cocoslime.data.model.GithubRepoResponse
import com.cocoslime.presentation.TestDispatcherRule
import com.cocoslime.presentation.data.service.TestGithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class PagingViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: PagingViewModel

    @Before
    fun setup() {
        viewModel = PagingViewModel(
            githubService = TestGithubService().apply {
                repoResponseList.addAll(
                    (0 until 100).map { index ->
                        createFakeGithubRepoResponse(index.toLong())
                    }
                )
            }
        )
    }

    @Test
    fun test_items_contain_0_to_50() = runTest {
        // Get the Flow of PagingData from the ViewModel under test
        val items: Flow<PagingData<GithubRepoResponse>> = viewModel.repoPagingFlow

        val itemsSnapshot: List<GithubRepoResponse> = items.asSnapshot {
            // Scroll to the 50th item in the list. This will also suspend till
            // the prefetch requirement is met if there's one.
            // It also suspends until all loading is complete.
            scrollTo(index = 50)
        }

        // With the asSnapshot complete, you can now verify that the snapshot
        // has the expected values
        assertEquals(
            expected = (0L until 50L).map(::createFakeGithubRepoResponse),
            actual = itemsSnapshot.subList(0, 50)
        )
    }
}