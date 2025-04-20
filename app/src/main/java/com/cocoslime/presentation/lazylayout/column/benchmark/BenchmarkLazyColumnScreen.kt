package com.cocoslime.presentation.lazylayout.column.benchmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.cocoslime.presentation.common.list.CommonListItemContainer
import com.cocoslime.presentation.common.list.createDummyListItems
import com.cocoslime.presentation.common.recomposeHighlighter
import com.cocoslime.presentation.lazylayout.column.component.EntryItem
import com.cocoslime.presentation.lazylayout.column.component.FooterItem
import com.cocoslime.presentation.lazylayout.column.component.HeaderItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun BenchmarkLazyColumnScreen(
    modifier: Modifier = Modifier
) {
    var data by remember {
        mutableStateOf(createDummyListItems(400))
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("lazy_column_screen"),
    ) { contentPadding ->
        ListItemSection(
            data = data.toImmutableList(),
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .testTag("list_of_items"),
            onClickItem = { clickedItem ->
                data = data.map {
                    if (it is CommonListItemContainer.Entry && it.id == clickedItem.id) {
                        it.copy(
                            content = it.content + ".Clicked"
                        )
                    } else {
                        it
                    }
                }
            }
        )
    }
}

@Composable
private fun ListItemSection(
    data: ImmutableList<CommonListItemContainer>,
    modifier: Modifier = Modifier,
    onClickItem: (CommonListItemContainer.Entry) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .recomposeHighlighter(),
    ) {
        items(
            count = data.size,
            key = { index ->
                data[index].identifier
            },
            contentType = { index ->
                data[index].contentType()
            }
        ) { index ->
            when (val item = data[index]) {
                is CommonListItemContainer.Header -> {
                    HeaderItem()
                }
                is CommonListItemContainer.Entry -> {
                    EntryItem(
                        item,
                        modifier = Modifier,
                        onClickItem = {
                            onClickItem(item)
                        }
                    )
                }
                is CommonListItemContainer.Footer -> {
                    FooterItem(item)
                }
            }
        }
    }
}

private fun CommonListItemContainer.contentType(): Int {
    return when (this) {
        is CommonListItemContainer.Header -> 0
        is CommonListItemContainer.Entry -> 1
        is CommonListItemContainer.Footer -> 2
    }
}

@Preview
@Composable
private fun EntryItemPreview() {
    MaterialTheme {
        EntryItem(
            item = CommonListItemContainer.Entry(
                id = 0,
                imageUrl = "https://picsum.photos/id/0/200",
                content = "This is a dummy content for item 0",
            )
        )
    }
}
