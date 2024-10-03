package com.cocoslime.presentation.navigation

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
import com.cocoslime.presentation.navigation.activity.SourceNavActivity
import com.cocoslime.presentation.navigation.compose.ComposeNavActivity

class NavigationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                NavigationScreen()
            }
        }
    }

    @Composable
    private fun NavigationScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RouteButton(
                clazz = SourceNavActivity::class.java,
                buttonText = "Activity -> Activity"
            )

            RouteButton(
                clazz = ComposeNavActivity::class.java,
                buttonText = "Compose NavHost"
            )
        }
    }

    @Composable
    fun <T> RouteButton(
        clazz: Class<T>,
        buttonText: String
    ) {
        Button(
            onClick = {
                startActivity(
                    Intent(
                        this@NavigationActivity,
                        clazz
                    )
                )
            },
        ) {
            Text(
                buttonText,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}