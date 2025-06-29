package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.fragment.xml

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// sealed class 를 사용하면, Fragment class was not set 크래시가 발생한다.
@Parcelize
data class XmlDestinationArgs(
    val message: String,
): Parcelable
