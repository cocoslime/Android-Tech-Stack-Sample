package com.cocoslime.presentation.navigation.activity

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection
import kotlinx.parcelize.Parcelize

class DestinationNavActivity: ComponentActivity() {

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
            ?.message

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->

                CommonSection(
                    title = this@DestinationNavActivity::class.simpleName.orEmpty(),
                    message = requestMessage.orEmpty(),
                    isTextFieldVisible = true,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    confirmButtonText = stringResource(id = R.string.prev_button_text),
                ) {
                    setResult(
                        RESULT_OK,
                        intent.putExtra(ResultData.EXTRA_KEY, ResultData(it))
                    )
                    finish()
                }
            }
        }
    }

}
