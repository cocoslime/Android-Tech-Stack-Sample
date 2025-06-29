package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.activity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
