package com.cocoslime.navigation.activity

import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cocoslime.navigation.screen.DestinationScreen
import kotlinx.parcelize.Parcelize

class DestinationActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setResult(RESULT_CANCELED)

        val title = if (Build.VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                RequestData.EXTRA_KEY,
                RequestData::class.java
            )
        } else {
            intent.getParcelableExtra<RequestData>(RequestData.EXTRA_KEY)
        }
            ?.title

        setContent {
            DestinationScreen(
                title = title.orEmpty()
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
        val title: String
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