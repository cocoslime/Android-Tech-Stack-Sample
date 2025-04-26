package com.cocoslime.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import com.cocoslime.common.CommonConst


fun MacrobenchmarkScope.startTaskActivity(task: String) =
    startActivityAndWait {
        it.putExtra(CommonConst.INTENT_EXTRA_URI, task)
    }
