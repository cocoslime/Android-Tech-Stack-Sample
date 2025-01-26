package com.cocoslime.presentation.lazylayout.column

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.cocoslime.presentation.BuildConfig
import com.cocoslime.presentation.common.base.BaseActivity

@OptIn(ExperimentalComposeUiApi::class)
class LazyColumnActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LazyColumnScreen(
                    modifier = Modifier
                        .semantics { this.testTagsAsResourceId = true }
                )
            }
        }

        if (BuildConfig.DEBUG) {
            Toast.makeText(
                this,
                "Debug mode 에서는 LazyLayout 의 성능 이슈가 있을 수 있다.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
