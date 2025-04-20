package com.cocoslime.benchmark

import android.graphics.Point
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.cocoslime.common.CommonConst
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LazyColumnScreenBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("EntryItem", TraceSectionMetric.Mode.Sum),
//            TraceSectionMetric("PublishDate.registerReceiver", TraceSectionMetric.Mode.Sum),
//            TraceSectionMetric("ItemTag", TraceSectionMetric.Mode.Sum)
        )

    @Test
    fun scrollUp() = benchmarkRule.measureRepeated(
        packageName = "com.cocoslime.presentation",
        metrics = metrics,
        compilationMode = CompilationMode.Full(),
        startupMode = StartupMode.WARM, // TODO: COLD 이면 에러(Why?)
        iterations = 1,
        setupBlock = {
            pressHome()
            startTaskActivity("${CommonConst.HOST_LAZY_COLUMN}/benchmark")
        },
        measureBlock = {
            val screenSearchCondition = Until.hasObject(By.res("lazy_column_screen"))
            device.wait(screenSearchCondition, 5_000).let { assertTrue(it) }

            val searchCondition = Until.hasObject(By.res("list_of_items"))
            device.wait(searchCondition, 5_000).let { assertTrue(it) }

            val lazyColumn = device.findObject(By.res("list_of_items"))
            lazyColumn.setGestureMargin(device.displayWidth / 5)

            repeat(2) {
                lazyColumn.drag(Point(lazyColumn.visibleCenter.x, lazyColumn.visibleBounds.top))
                Thread.sleep(500)
            }
        }
    )
}
