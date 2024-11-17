package com.cocoslime.presentation.common.list

val dummyListItems = buildList<CommonListItemContainer> {
    add(CommonListItemContainer.Header)
    repeat(20) { index ->
        add(
            CommonListItemContainer.Entry(
                id = index.toLong(),
                imageUrl = "https://picsum.photos/id/$index/200",
                content = "This is a dummy content for item $index",
            ),
        )
    }
    add(
        CommonListItemContainer.Footer(
            message = "This is a dummy footer",
        )
    )
}