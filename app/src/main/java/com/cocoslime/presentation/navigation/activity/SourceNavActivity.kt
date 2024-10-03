package com.cocoslime.presentation.navigation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.cocoslime.presentation.R
import com.cocoslime.presentation.screen.CommonScreen

class SourceNavActivity : ComponentActivity() {

    private val destinationActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            result.data?.getParcelableExtra(
                DestinationNavActivity.ResultData.EXTRA_KEY,
                DestinationNavActivity.ResultData::class.java
            )
        } else {
            result.data?.getParcelableExtra<DestinationNavActivity.ResultData>(DestinationNavActivity.ResultData.EXTRA_KEY)
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
                CommonScreen(
                    title = this::class.simpleName.orEmpty(),
                    message = resultFromDestination,
                    confirmButtonText = stringResource(id = R.string.next_button_text)
                ) { requestMessage ->
                    destinationActivity.launch(
                        Intent(
                            this@SourceNavActivity,
                            DestinationNavActivity::class.java
                        )
                            .apply {
                                putExtra(
                                    DestinationNavActivity.RequestData.EXTRA_KEY,
                                    DestinationNavActivity.RequestData(requestMessage)
                                )
                            }
                    )
                }
            }
        }
    }

}