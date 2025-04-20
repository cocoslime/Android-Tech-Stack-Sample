package com.cocoslime.presentation.lazylayout.column

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.cocoslime.presentation.BuildConfig
import com.cocoslime.presentation.common.base.BaseActivity
import com.cocoslime.presentation.lazylayout.column.benchmark.BenchmarkLazyColumnScreen
import com.cocoslime.presentation.lazylayout.column.recomposition.RecompositionLazyColumnScreen

@OptIn(ExperimentalComposeUiApi::class)
class LazyColumnActivity: BaseActivity() {
    private var type by mutableStateOf<Type?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                when (type) {
                    Type.RECOMPOSITION -> {
                        RecompositionLazyColumnScreen()
                    }
                    Type.BENCHMARK -> {
                        BenchmarkLazyColumnScreen(
                            modifier = Modifier
                                .semantics { this.testTagsAsResourceId = true }
                        )
                    }
                    else -> { }
                }
            }
        }

        if (BuildConfig.DEBUG) {
            Toast.makeText(
                this,
                "Debug mode 에서는 LazyLayout 의 성능 이슈가 있을 수 있다.",
                Toast.LENGTH_LONG
            ).show()
        }

        intent?.let {
            type = when (it.getStringExtra(INTENT_EXTRA_KEY_TYPE)) {
                Type.RECOMPOSITION.name -> Type.RECOMPOSITION
                Type.BENCHMARK.name -> Type.BENCHMARK
                else -> null
            }
        }
    }

    private enum class Type {
        BENCHMARK,
        RECOMPOSITION
    }

    companion object {
        const val INTENT_EXTRA_KEY_TYPE = "type"
    }
}
