package com.cocoslime.presentation.common.list

val dummyListItems = createDummyListItems(20)
private val starTimeMillis = System.currentTimeMillis()

fun createDummyItem() = CommonListItemContainer.Entry(
    id = starTimeMillis,
    imageUrl = "https://picsum.photos/id/${starTimeMillis % 100}/200",
    content = "new Item: $starTimeMillis",
)

fun createDummyListItems(itemCount: Int) = buildList<CommonListItemContainer> {
    add(CommonListItemContainer.Header)
    repeat(itemCount) { index ->
        add(
            CommonListItemContainer.Entry(
                id = starTimeMillis + index,
                imageUrl = "https://picsum.photos/id/$index/200",
                content = "[$index] Lorem ipsum ...",
            ),
        )
    }
    add(
        CommonListItemContainer.Footer(
            message = "This is a dummy footer",
        )
    )
}
