package com.cocoslime.presentation.common.base

import android.os.Bundle
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate ${this::class.java.simpleName}".let(::println)
    }
}
