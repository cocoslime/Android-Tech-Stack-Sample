package com.cocoslime.presentation.navigation.fragment

import kotlinx.serialization.Serializable

sealed interface FragmentNavRoute {

    @Serializable
    data class DestinationArgs(
        val message: String
    ): FragmentNavRoute

    @Serializable
    data object Source: FragmentNavRoute
}

