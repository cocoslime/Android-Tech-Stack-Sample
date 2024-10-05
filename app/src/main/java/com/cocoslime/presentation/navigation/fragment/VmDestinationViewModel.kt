package com.cocoslime.presentation.navigation.fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VmDestinationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    val args: FragmentNavRoute.VmDestinationArgs
        get() = savedStateHandle.toRoute<FragmentNavRoute.VmDestinationArgs>()
}