package com.cocoslime.presentation.navigation.fragment.dsl

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute

class VmDestinationViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val args: FragmentNavRoute.VmDestinationArgs
        get() = savedStateHandle.toRoute<FragmentNavRoute.VmDestinationArgs>()
}
