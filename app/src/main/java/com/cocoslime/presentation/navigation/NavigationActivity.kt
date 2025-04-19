package com.cocoslime.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cocoslime.presentation.common.StartActivityButton
import com.cocoslime.presentation.navigation.activity.SourceNavActivity
import com.cocoslime.presentation.navigation.compose.ComposeNavActivity
import com.cocoslime.presentation.navigation.fragment.dsl.FragmentNavActivity
import com.cocoslime.presentation.navigation.fragment.xml.XmlFragmentNavActivity

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
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StartActivityButton(
                activity = this@NavigationActivity,
                clazz = SourceNavActivity::class.java,
                buttonText = "Activity -> Activity"
            )

            StartActivityButton(
                activity = this@NavigationActivity,
                clazz = ComposeNavActivity::class.java,
                buttonText = "Compose NavHost"
            )

            StartActivityButton(
                activity = this@NavigationActivity,
                clazz = FragmentNavActivity::class.java,
                buttonText = "Fragment NavHost"
            )

            StartActivityButton(
                activity = this@NavigationActivity,
                clazz = XmlFragmentNavActivity::class.java,
                buttonText = "Fragment NavHost with XML"
            )
        }
    }

}
