package com.cocoslime.presentation.navigation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection
import com.cocoslime.presentation.common.FRAGMENT_RESULT_KEY

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
                SourceScreen {
                    findNavController().navigate(
                        route = FragmentNavRoute.DestinationArgs(
                            message = it
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun SourceScreen(
        onNavigateDestination: (String) -> Unit
    ) {
        var message by remember { mutableStateOf("") }

        DisposableEffect(Unit) {
            "DisposableEffect".also(::println)

            setDestinationResultListener {
                message = it.resultMessage
            }

            onDispose {
                "onDispose".also(::println)
                clearFragmentResultListener(DestinationFragment.Result.KEY)
            }
        }

        MaterialTheme {
            CommonSection(
                title = getString(R.string.source_screen_title),
                message = message,
                isTextFieldVisible = true,
                confirmButtonText = getString(R.string.next_button_text),
                modifier = Modifier.fillMaxSize(),
            ) { message ->
                onNavigateDestination(message)
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