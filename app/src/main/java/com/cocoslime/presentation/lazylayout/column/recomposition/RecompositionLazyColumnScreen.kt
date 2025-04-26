package com.cocoslime.presentation.lazylayout.column.recomposition

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.list.CommonListItemContainer
import com.cocoslime.presentation.common.list.createDummyItem
import com.cocoslime.presentation.common.list.createDummyListItems
import com.cocoslime.presentation.common.recomposeHighlighter
import com.cocoslime.presentation.lazylayout.column.component.FooterItem
import com.cocoslime.presentation.lazylayout.column.component.HeaderItem
import com.cocoslime.presentation.lazylayout.column.component.contentType
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

private const val TAG = "RecompositionTest"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecompositionLazyColumnScreen(
    modifier: Modifier = Modifier,
    initialData: List<CommonListItemContainer> = createDummyListItems(10),
) {
    val data = remember(initialData) {
        initialData.toMutableStateList()
    }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            // 상단 컨트롤 영역
            FlowRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp, Alignment.Start),
            ) {
                Button(
                    onClick = {
                        // 항목 추가
                        data.add(createDummyItem())
                    },
                ) {
                    Text("Add Item")
                }

                Button(onClick = {
                    val middle = data.size / 2
                    data.add(middle, createDummyItem())
                }) {
                    // 중간에 항목 추가
                    Text("Insert Middle")
                }

                Button(onClick = {
                    if (data.size > 1) { data.removeAt(1) }
                }) {
                    Text("Remove First")
                }

                Button(onClick = {
                    data.shuffled()
                }) {
                    Text("Shuffle")
                }

                Button(onClick = {
                    data.clear()
                    data.addAll(initialData)
                }) {
                    // 초기 데이터로 복원
                    Text("Restore")
                }
            }

            // 탭 선택
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Without Key") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("With Key") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Key\nContentType") }
                )
            }

            // LazyColumn 표시
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                when (selectedTab) {
                    0 -> LazyColumnWithoutKey(data.toImmutableList())
                    1 -> LazyColumnWithKey(data.toImmutableList())
                    2 -> LazyColumnWithKeyContentType(data.toImmutableList())
                }
            }

        }
    }
}

@Composable
private fun LazyColumnWithoutKey(
    data: ImmutableList<CommonListItemContainer>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .recomposeHighlighter(),
    ) {
        items(
            count = data.size,
        ) { index ->
            when (val item = data[index]) {
                is CommonListItemContainer.Header -> {
                    HeaderItem()
                }
                is CommonListItemContainer.Entry -> {
                    RecompositionEntryItem(
                        item,
                        keyTypeString = "withoutKey",
                        modifier = Modifier,
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
private fun LazyColumnWithKey(
    data: ImmutableList<CommonListItemContainer>,
    modifier: Modifier = Modifier,
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
        ) { index ->
            when (val item = data[index]) {
                is CommonListItemContainer.Header -> {
                    HeaderItem()
                }
                is CommonListItemContainer.Entry -> {
                    RecompositionEntryItem(
                        item,
                        keyTypeString = "withKey",
                        modifier = Modifier,
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
private fun LazyColumnWithKeyContentType(
    data: ImmutableList<CommonListItemContainer>,
    modifier: Modifier = Modifier,
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
                    RecompositionEntryItem(
                        item,
                        keyTypeString = "withKeyContentType",
                        modifier = Modifier,
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
private fun RecompositionEntryItem(
    item: CommonListItemContainer.Entry,
    keyTypeString: String,
    modifier: Modifier = Modifier,
) {
    // 각 항목의 재구성 횟수 추적
    val recompositionCount = remember { mutableIntStateOf(0) }

    // SideEffect 를 사용하여 로깅 (재구성될 때마다 호출됨)
    SideEffect {
        recompositionCount.intValue++
        Log.d(TAG, "Item ${item.content} $keyTypeString recomposed: ${recompositionCount.intValue} times")
    }

    Column (
        modifier = modifier
    ) {
        ListItem(
            headlineContent = { Text(text = item.content) },
            modifier = Modifier
                .fillMaxWidth(),
            supportingContent = {
                Text(text = "Recomposition(${recompositionCount.intValue})")
            },
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
