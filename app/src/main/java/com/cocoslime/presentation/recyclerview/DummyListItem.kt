package com.cocoslime.presentation.recyclerview

val dummyListItems = listOf(
    ListItem.Header,
    ListItem.Entry(
        id = 1,
        imageUrl = "https://picsum.photos/id/1/200",
        content = "This is a dummy content 1",
    ),
    ListItem.Entry(
        id = 2,
        imageUrl = "https://picsum.photos/id/2/200",
        content = "This is a dummy content 2",
    ),
    ListItem.Entry(
        id = 3,
        imageUrl = "https://picsum.photos/id/3/200",
        content = "This is a dummy content 3",
    ),
//    ListItem.Footer(
//        message = "This is a dummy footer",
//    ),
)