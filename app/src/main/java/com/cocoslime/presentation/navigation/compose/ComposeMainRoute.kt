package com.cocoslime.presentation.navigation.compose

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

class ComposeMainRoute {
    @Keep
    @Serializable
    object Source

    @Keep
    @Serializable
    data class DestinationArgs(
        val message: String
    )

    @Parcelize
    data class DestinationResult(
        val resultMessage: String
    ): Parcelable {
        companion object {
            val KEY = this::class.qualifiedName!!

            val DEFAULT = DestinationResult("")
        }
    }

    @Keep
    @Serializable
    data class OtherDestinationArgs(
        val message: String
    )
}
