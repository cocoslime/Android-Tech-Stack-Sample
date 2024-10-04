package com.cocoslime.presentation.navigation.compose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

class ComposeMainRoute {

    @Serializable
    object Source


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

    @Serializable
    data class OtherDestinationArgs(
        val message: String
    )
}
