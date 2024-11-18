package com.cocoslime.presentation.lazylayout.column

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cocoslime.presentation.common.list.CommonListItemContainer
import com.cocoslime.presentation.common.list.dummyListItems

@Composable
fun LazyColumnScreen() {
    val data = dummyListItems

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        ListItemSection(
            data = data,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
    }
}

@Composable
private fun ListItemSection(
    data: List<CommonListItemContainer>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
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
                    EntryItem(item)
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
private fun EntryItem(item: CommonListItemContainer.Entry) {
    Column {
        ListItem(
            headlineContent = { Text(text = item.content) },
            modifier = Modifier
                .fillMaxWidth(),
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