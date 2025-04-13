package com.cocoslime.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cocoslime.presentation.architecture.orbit.OrbitActivity
import com.cocoslime.presentation.common.StartActivityButton
import com.cocoslime.presentation.common.base.BaseActivity
import com.cocoslime.presentation.lazylayout.column.LazyColumnActivity
import com.cocoslime.presentation.navigation.NavigationActivity
import com.cocoslime.presentation.paging.PagingActivity
import com.cocoslime.presentation.recyclerview.RecyclerViewActivity
import com.cocoslime.presentation.viewpager.ViewPagerActivity

const val EXTRA_START_TASK = "EXTRA_START_TASK"

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }

        initStartCondition()
    }

    private fun initStartCondition() {
        val startFromStep = intent.getStringExtra(EXTRA_START_TASK)
        when(startFromStep) {
            "LazyColumn" -> {
                startActivity(Intent(this, LazyColumnActivity::class.java))
            }
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
            StartActivityButton(
                activity = this@MainActivity,
                clazz = LazyColumnActivity::class.java,
                buttonText = "LazyColumn",
            )
        }
    }
}
