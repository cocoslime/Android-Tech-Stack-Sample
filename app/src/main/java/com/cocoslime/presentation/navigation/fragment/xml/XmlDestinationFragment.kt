package com.cocoslime.presentation.navigation.fragment.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.cocoslime.presentation.R
import com.cocoslime.presentation.navigation.fragment.component.FragmentDestinationScreen


class XmlDestinationFragment : Fragment() {
    private val args: XmlDestinationFragmentArgs by navArgs()

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
                    FragmentDestinationScreen(
                        title = stringResource(R.string.destination_screen_title),
                        message = args.data?.message ?: "",
                        onConfirm = {
                            findNavController()
                                .previousBackStackEntry
                                ?.savedStateHandle
                                ?.set(KEY_RESULT, it)
                            findNavController().popBackStack(
                                destinationId = R.id.sourceFragment,
                                inclusive = false,
                            )
                        },
                    )
                }
            }
        }
    }

    companion object {
        const val KEY_RESULT = "key_result"
    }
}
