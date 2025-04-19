package com.cocoslime.presentation.navigation.fragment.dsl

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.cocoslime.presentation.common.FRAGMENT_RESULT_KEY
import com.cocoslime.presentation.navigation.fragment.component.SourceScreen

class SourceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    var message by remember { mutableStateOf("") }

                    DisposableEffect(Unit) {
                        setDestinationResultListener {
                            message = it.resultMessage
                        }

                        onDispose {
                            clearFragmentResultListener(DestinationFragment.Result.KEY)
                        }
                    }

                    var otherMessage by remember { mutableStateOf("") }

                    LaunchedEffect(key1 = Unit) {
                        // ViewModel 의 SavedStateHandle 로 가져올 수 없음
                        findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow<VmDestinationFragment.Result?>(
                            key = VmDestinationFragment.Result.KEY,
                            initialValue = null
                        )?.collect {
                            otherMessage = it?.resultMessage ?: ""
                        }
                    }

                    SourceScreen(
                        message = message,
                        otherMessage = otherMessage,
                        navigateToDestination = {
                            findNavController().navigate(
                                route = FragmentNavRoute.DestinationArgs(
                                    message = it
                                )
                            )
                        },
                        navigateToVmDestination = {
                            findNavController().navigate(
                                route = FragmentNavRoute.VmDestinationArgs(
                                    message = it
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    private fun setDestinationResultListener(
        listener: (DestinationFragment.Result) -> Unit
    ) {
        // Use FragmentResult API
        // https://developer.android.com/guide/fragments/communicate#fragment-result
        setFragmentResultListener(
            DestinationFragment.Result.KEY,
        ) { key, bundle ->
            // Handle the result
            val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(FRAGMENT_RESULT_KEY, DestinationFragment.Result::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable<DestinationFragment.Result>(FRAGMENT_RESULT_KEY)
            }
            result?.let(listener)
        }
    }
}
