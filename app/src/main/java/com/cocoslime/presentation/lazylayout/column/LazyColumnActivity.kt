package com.cocoslime.presentation.lazylayout.column

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.cocoslime.presentation.BuildConfig
import com.cocoslime.common.base.BaseActivity
import com.cocoslime.presentation.lazylayout.column.benchmark.BenchmarkLazyColumnScreen
import com.cocoslime.presentation.lazylayout.column.recomposition.RecompositionLazyColumnScreen

@OptIn(ExperimentalComposeUiApi::class)
class LazyColumnActivity: BaseActivity() {
    private var type by mutableStateOf<Type?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val typeString = intent?.getStringExtra(INTENT_EXTRA_KEY_TYPE)
            ?: savedInstanceState?.getString(INTENT_EXTRA_KEY_TYPE)
                .also { println("From SavedInstanceState: $it") }

        type = when (typeString) {
            Type.RECOMPOSITION.name -> {
                Type.RECOMPOSITION
            }
            Type.BENCHMARK.name -> {
                Type.BENCHMARK
            }
            else -> {
                throw IllegalStateException("Unknown type: $typeString")
            }
        }

        setContent {
            MaterialTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    if (BuildConfig.DEBUG) {
                        snackbarHostState.showSnackbar(
                            message = "Debug mode: LazyLayout 의 성능 이슈",
                        )
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    when (type) {
                        Type.RECOMPOSITION -> {
                            RecompositionLazyColumnScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                        Type.BENCHMARK -> {
                            BenchmarkLazyColumnScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .semantics { this.testTagsAsResourceId = true }
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putString(INTENT_EXTRA_KEY_TYPE, type?.name)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val typeString = intent.getStringExtra(INTENT_EXTRA_KEY_TYPE)

        type = when (typeString) {
            Type.RECOMPOSITION.name -> {
                Type.RECOMPOSITION
            }
            Type.BENCHMARK.name -> {
                Type.BENCHMARK
            }
            else -> {
                Type.RECOMPOSITION
            }
        }
    }

    private enum class Type {
        BENCHMARK,
        RECOMPOSITION,
    }

    companion object {
        const val INTENT_EXTRA_KEY_TYPE = "type"
    }
}
