package com.cocoslime.presentation.paging


import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.cocoslime.common.createFakeGithubRepoResponse
import com.cocoslime.presentation.TestDispatcherRule
import com.cocoslime.presentation.data.service.TestGithubService
import com.cocoslime.presentation.paging.PagingViewModel.UiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

/*
https://developer.android.com/topic/libraries/architecture/paging/test?hl=ko#ui-layer-tests
 */
class PagingViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: PagingViewModel

    @Before
    fun setup() {
        viewModel = PagingViewModel(
            githubService = TestGithubService().apply {
                repoResponseList.addAll(
                    (0 until MAX_RESPONSE_SIZE).map { index ->
                        createFakeGithubRepoResponse(index.toLong())
                    }
                )
            }
        )
    }

    @Test
    fun test_items_contain_0_to_50() = runTest {
        // Get the Flow of PagingData from the ViewModel under test
        val items: Flow<PagingData<PagingViewModel.UiModel>> = viewModel.repoPagingFlow

        val itemsSnapshot: List<PagingViewModel.UiModel> = items.asSnapshot {
            // Scroll to the 50th item in the list. This will also suspend till
            // the prefetch requirement is met if there's one.
            // It also suspends until all loading is complete.
            scrollTo(index = 50)
        }

        // With the asSnapshot complete, you can now verify that the snapshot
        // has the expected values
        assertEquals(
            expected = (0L until 50L)
                .map(::createFakeGithubRepoResponse)
                .map { UiModel.UserModel.fromDataModel(it) },
            actual = itemsSnapshot
                .filterIsInstance<UiModel.UserModel>()
                .subList(0, 50)
        )
    }

    @Test
    fun test_footer_is_visible() = runTest {
        // Get the Flow of PagingData from the ViewModel under test
        val items: Flow<PagingData<UiModel>> = viewModel.repoPagingFlow

        val itemsSnapshot: List<UiModel> = items.asSnapshot {
            // Scroll till the footer is visible
            appendScrollWhile { item: UiModel ->
                item is UiModel.UserModel || (item is UiModel.SeparatorModel && item.description != "FOOTER")
            }
        }

        assertEquals(
            expected = MAX_RESPONSE_SIZE,
            actual = itemsSnapshot
                .filterIsInstance<UiModel.UserModel>()
                .size
        )
    }

    companion object {
        private const val MAX_RESPONSE_SIZE = 100
    }
}