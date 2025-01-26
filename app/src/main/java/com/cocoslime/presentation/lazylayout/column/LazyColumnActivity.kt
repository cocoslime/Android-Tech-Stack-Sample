package com.cocoslime.presentation.lazylayout.column

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.cocoslime.presentation.BuildConfig
import com.cocoslime.presentation.common.base.BaseActivity

class LazyColumnActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                LazyColumnScreen()
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
