package com.cocoslime.presentation.lazylayout.column

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import coil.compose.rememberAsyncImagePainter
import com.cocoslime.presentation.common.list.CommonListItemContainer
import com.cocoslime.presentation.common.list.createDummyListItems
import com.cocoslime.presentation.common.recomposeHighlighter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun LazyColumnScreen(
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

@Composable
private fun HeaderItem() {
    Column {
        Text(
            text = "HEADER",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )

        HorizontalDivider()
    }
}

@Composable
private fun EntryItem(
    item: CommonListItemContainer.Entry,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {}
) = trace("EntryItem") {
    Column (
        modifier = modifier
    ) {
        ListItem(
            headlineContent = { Text(text = item.content) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickItem() },
            overlineContent = { Text(text = "ID ${item.id}") },
            leadingContent = {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = rememberAsyncImagePainter(
                        model = item.imageUrl
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            },
        )
        HorizontalDivider()
    }
}

@Composable
private fun FooterItem(item: CommonListItemContainer.Footer) {
    Text(
        text = item.message,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    )
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
