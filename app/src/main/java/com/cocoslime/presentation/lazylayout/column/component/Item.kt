package com.cocoslime.presentation.lazylayout.column.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.list.CommonListItemContainer
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun HeaderItem() {
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
fun EntryItem(
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
                CoilImage(
                    imageModel = { item.imageUrl }, // loading a network image using an URL.
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier.size(100.dp),
                    previewPlaceholder = painterResource(id = R.drawable.ic_launcher_foreground),
                )
            },
        )
        HorizontalDivider()
    }
}

@Composable
fun FooterItem(item: CommonListItemContainer.Footer) {
    Text(
        text = item.message,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    )
}
