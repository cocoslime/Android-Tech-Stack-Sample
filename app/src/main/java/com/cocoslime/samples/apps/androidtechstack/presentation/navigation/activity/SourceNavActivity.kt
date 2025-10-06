package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.presentation.common.CommonSection

class SourceNavActivity : ComponentActivity() {

    private val destinationResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            result.data?.getParcelableExtra(
                ResultData.EXTRA_KEY,
                ResultData::class.java
            )
        } else {
            result.data?.getParcelableExtra<ResultData>(ResultData.EXTRA_KEY)
        }

        resultFromDestinationActivity.value = resultData?.result.orEmpty()
    }

    private val resultFromDestinationActivity = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val resultFromDestination by resultFromDestinationActivity

            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        Text(
                            text = resultFromDestination,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        CommonSection(
                            title = "Destination",
                            isTextFieldVisible = true,
                            confirmButtonText = stringResource(id = R.string.next_button_text)
                        ) { requestMessage ->
                            destinationResultLauncher.launch(
                                Intent(
                                    this@SourceNavActivity,
                                    DestinationNavActivity::class.java
                                )
                                    .apply {
                                        putExtra(
                                            RequestData.EXTRA_KEY,
                                            RequestData(requestMessage)
                                        )
                                    }
                            )
                        }

                        CommonSection(
                            title = "Bridge",
                            isTextFieldVisible = true,
                            confirmButtonText = stringResource(id = R.string.next_button_text)
                        ) { requestMessage ->
                            destinationResultLauncher.launch(
                                Intent(
                                    this@SourceNavActivity,
                                    BridgeNavActivity::class.java
                                )
                                    .apply {
                                        putExtra(
                                            RequestData.EXTRA_KEY,
                                            RequestData(requestMessage)
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

}
