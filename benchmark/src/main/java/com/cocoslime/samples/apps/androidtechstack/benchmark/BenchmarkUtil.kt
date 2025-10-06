package com.cocoslime.samples.apps.androidtechstack.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import com.cocoslime.samples.apps.androidtechstack.common.CommonConst


fun MacrobenchmarkScope.startTaskActivity(task: String) =
    startActivityAndWait {
        it.putExtra(CommonConst.INTENT_EXTRA_URI, task)
    }
