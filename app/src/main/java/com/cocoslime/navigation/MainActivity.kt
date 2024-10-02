package com.cocoslime.navigation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cocoslime.navigation.activity.DestinationActivity

class MainActivity : ComponentActivity() {


    private val destinationActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            result.data?.getParcelableExtra(
                DestinationActivity.ResultData.EXTRA_KEY,
                DestinationActivity.ResultData::class.java
            )
        } else {
            result.data?.getParcelableExtra<DestinationActivity.ResultData>(DestinationActivity.ResultData.EXTRA_KEY)
        }

        resultFromDestinationActivity.value = resultData?.result.orEmpty()
    }

    private val resultFromDestinationActivity = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme { MainScreen() }
        }
    }

    @Composable
    private fun MainScreen() {
        val state by resultFromDestinationActivity

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state
            )

            TextButton(onClick = {
                destinationActivity.launch(
                    Intent(
                        this@MainActivity,
                        DestinationActivity::class.java
                    )
                        .apply {
                            putExtra(
                                DestinationActivity.RequestData.EXTRA_KEY,
                                DestinationActivity.RequestData("FromMain")
                            )
                        }
                )
            }) {
                Text(
                    "Activity",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}