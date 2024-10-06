package com.cocoslime.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cocoslime.presentation.common.StartActivityButton
import com.cocoslime.presentation.navigation.NavigationActivity
import com.cocoslime.presentation.navigation.activity.SourceNavActivity
import com.cocoslime.presentation.recyclerview.RecyclerViewActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }

    @Composable
    private fun MainScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
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
        }
    }
}