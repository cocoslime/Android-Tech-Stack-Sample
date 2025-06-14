package com.cocoslime.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cocoslime.common.CommonConst
import com.cocoslime.common.CommonConst.HOST_LAZY_COLUMN
import com.cocoslime.presentation.architecture.orbit.OrbitActivity
import com.cocoslime.feature.circuit.CircuitActivity
import com.cocoslime.presentation.common.StartActivityButton
import com.cocoslime.common.base.BaseActivity
import com.cocoslime.presentation.lazylayout.column.LazyColumnActivity
import com.cocoslime.presentation.navigation.NavigationActivity
import com.cocoslime.presentation.paging.PagingActivity
import com.cocoslime.presentation.recyclerview.RecyclerViewActivity
import com.cocoslime.presentation.viewpager.ViewPagerActivity
import com.cocoslime.common.printlnDebug

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }

        intent?.let { initStartCondition(it) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        initStartCondition(intent)
    }

    private fun initStartCondition(intent: Intent) {
        val uriFromStartIntent = intent.getStringExtra(CommonConst.INTENT_EXTRA_URI)
        uriFromStartIntent?.let { uriString ->
            val uri = parseFromIntentUri(uriString)
            uri.printlnDebug()
            when (uri.host) {
                HOST_LAZY_COLUMN -> {
                    startActivity(
                        Intent(this, LazyColumnActivity::class.java).apply {
                            putExtra(LazyColumnActivity.INTENT_EXTRA_KEY_TYPE, uri.pathSegments.firstOrNull())
                        }
                    )
                }
            }
        }
    }

    private fun parseFromIntentUri(uriString: String): Uri {
        return try {
            // "Host/Path" 형식을 표준 Uri 형식으로 변환
            val normalizedUri = if (!uriString.contains("://") && !uriString.startsWith("/")) {
                // 스키마가 없는 경우 임의의 스키마 추가 (파싱 목적으로만 사용)
                "${CommonConst.SCHEME}$uriString"
            } else {
                uriString
            }

            Uri.parse(normalizedUri)
        } catch (e: Exception) {
            // 예외 발생 시 기본 Uri 반환
            Uri.parse(CommonConst.SCHEME)
        }
    }


    @Composable
    private fun MainScreen() {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StartActivityButton(
                activity = this@MainActivity,
                clazz = NavigationActivity::class.java,
                buttonText = "Navigation",
            )

            StartActivityButton(
                activity = this@MainActivity,
                clazz = RecyclerViewActivity::class.java,
                buttonText = "RecyclerView",
            )

            StartActivityButton(
                activity = this@MainActivity,
                clazz = ViewPagerActivity::class.java,
                buttonText = "ViewPager",
            )

            StartActivityButton(
                activity = this@MainActivity,
                clazz = OrbitActivity::class.java,
                buttonText = "Mvi-Orbit",
            )

            StartActivityButton(
                activity = this@MainActivity,
                clazz = PagingActivity::class.java,
                buttonText = "Paging 3",
            )

            // LazyColumnActivity
            Text(
                text = "LazyColumn",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall,
            )

            Row (
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                StartActivityButton(
                    activity = this@MainActivity,
                    clazz = LazyColumnActivity::class.java,
                    buttonText = "BENCHMARK",
                    modifier = Modifier.weight(1f),
                ) {
                    it.putExtra(
                        LazyColumnActivity.INTENT_EXTRA_KEY_TYPE,
                        "BENCHMARK"
                    )
                }

                StartActivityButton(
                    activity = this@MainActivity,
                    clazz = LazyColumnActivity::class.java,
                    buttonText = "RECOMPOSITION",
                    modifier = Modifier.weight(1f),
                ) {
                    it.putExtra(
                        LazyColumnActivity.INTENT_EXTRA_KEY_TYPE,
                        "RECOMPOSITION"
                    )
                }
            }

            StartActivityButton(
                activity = this@MainActivity,
                clazz = CircuitActivity::class.java,
                buttonText = "Circuit",
            )
        }
    }
}
