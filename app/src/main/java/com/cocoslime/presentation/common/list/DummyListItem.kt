package com.cocoslime.presentation.common.list

val dummyListItems = createDummyListItems(20)

fun createDummyListItems(itemCount: Int) = buildList<CommonListItemContainer> {
    add(CommonListItemContainer.Header)
    repeat(itemCount) { index ->
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
