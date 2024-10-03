package com.cocoslime.presentation.navigation.activity

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import com.cocoslime.presentation.R
import com.cocoslime.presentation.screen.CommonScreen
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
            CommonScreen(
                title = this@DestinationNavActivity::class.simpleName.orEmpty(),
                message = requestMessage.orEmpty(),
                confirmButtonText = stringResource(id = R.string.prev_button_text)
            ) {
                setResult(
                    RESULT_OK,
                    intent.putExtra(ResultData.EXTRA_KEY, ResultData(it))
                )
                finish()
            }
        }
    }

    @Parcelize
    data class RequestData(
        val message: String
    ): Parcelable {

        companion object {
            val EXTRA_KEY = this::class.qualifiedName
        }
    }

    @Parcelize
    data class ResultData(
        val result: String
    ): Parcelable {
        companion object {
            val EXTRA_KEY = this::class.qualifiedName
        }
    }
}