package com.cocoslime.presentation.navigation.fragment.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cocoslime.presentation.navigation.fragment.component.SourceScreen
import kotlinx.coroutines.flow.onEach

class XmlSourceFragment : Fragment() {

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
                    var otherMessage by remember {
                        mutableStateOf("")
                    }

                    LaunchedEffect(Unit) {
                        otherMessage = findNavController().currentBackStackEntry?.savedStateHandle
                            ?.getStateFlow(
                                key = XmlDestinationFragment.KEY_RESULT,
                                initialValue = ""
                            )
                            ?.value ?: ""
                    }

                    SourceScreen(
                        message = "Test",
                        otherMessage = otherMessage,
                        navigateToDestination = {
                            findNavController().navigate(
                                XmlSourceFragmentDirections.actionSourceFragmentToDestinationFragment(
                                    it
                                )
                            )
                        },
                        navigateToVmDestination = {

                        }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
