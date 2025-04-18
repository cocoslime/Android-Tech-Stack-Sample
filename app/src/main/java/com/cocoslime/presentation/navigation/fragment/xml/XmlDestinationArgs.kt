package com.cocoslime.presentation.navigation.fragment.xml

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class XmlDestinationArgs(
    val message: String,
): Parcelable
