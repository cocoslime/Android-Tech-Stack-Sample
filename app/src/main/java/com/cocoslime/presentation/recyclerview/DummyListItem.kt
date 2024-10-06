package com.cocoslime.presentation.recyclerview

val dummyListItems = buildList<ListItem> {
    add(ListItem.Header)
    repeat(20) { index ->
        add(
            ListItem.Entry(
                id = index.toLong(),
                imageUrl = "https://picsum.photos/id/$index/200",
                content = "This is a dummy content for item $index",
            ),
        )
    }
    add(
        ListItem.Footer(
            message = "This is a dummy footer",
        )
    )
}