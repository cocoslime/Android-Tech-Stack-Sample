package com.cocoslime.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope


fun MacrobenchmarkScope.startTaskActivity(task: String) =
    startActivityAndWait { it.putExtra("EXTRA_START_TASK", task) }
