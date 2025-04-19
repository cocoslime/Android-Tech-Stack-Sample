package com.cocoslime.presentation.navigation.fragment.dsl

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

sealed interface FragmentNavRoute {

    @Keep
    @Serializable
    data class VmDestinationArgs(
        val message: String
    ): FragmentNavRoute

    @Keep
    @Serializable
    data class DestinationArgs(
        val message: String
    ): FragmentNavRoute

    @Keep
    @Serializable
    data object Source: FragmentNavRoute
}

