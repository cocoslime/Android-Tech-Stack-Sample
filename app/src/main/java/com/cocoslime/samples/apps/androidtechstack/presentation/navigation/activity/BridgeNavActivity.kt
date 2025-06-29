package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.activity

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.presentation.common.CommonSection

class BridgeNavActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        val requestMessage = if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                RequestData.EXTRA_KEY,
                RequestData::class.java
            )
        } else {
            intent.getParcelableExtra<RequestData>(RequestData.EXTRA_KEY)
        }
            ?.message.orEmpty()

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                ) {
                    Text(
                        text = requestMessage,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    CommonSection(
                        title = "Back to Source",
                        message = null,
                        isTextFieldVisible = true,
                        modifier = Modifier
                            .weight(1f),
                        confirmButtonText = stringResource(id = R.string.prev_button_text),
                    ) {
                        setResult(
                            RESULT_OK,
                            intent.putExtra(ResultData.EXTRA_KEY, ResultData(it))
                        )
                        finish()
                    }

                    CommonSection(
                        title = "Next to Destination",
                        message = null,
                        isTextFieldVisible = false,
                        modifier = Modifier
                            .weight(1f),
                        confirmButtonText = stringResource(id = R.string.next_button_text)
                    ) {
                        startActivity(
                            Intent(
                                this@BridgeNavActivity,
                                DestinationNavActivity::class.java
                            )
                                .apply {
                                    setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                                    putExtra(
                                        RequestData.EXTRA_KEY,
                                        RequestData(requestMessage)
                                    )
                                }
                        )
                        finish()
                    }
                }
            }
        }
    }

}
