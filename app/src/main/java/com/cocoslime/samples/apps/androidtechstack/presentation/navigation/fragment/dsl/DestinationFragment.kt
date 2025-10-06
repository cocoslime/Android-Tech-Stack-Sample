package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.fragment.dsl

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.presentation.common.FRAGMENT_RESULT_KEY
import com.cocoslime.samples.apps.androidtechstack.presentation.navigation.fragment.component.FragmentDestinationScreen
import kotlinx.parcelize.Parcelize

class DestinationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args = findNavController()
            .getBackStackEntry<FragmentNavRoute.DestinationArgs>()
            .toRoute<FragmentNavRoute.DestinationArgs>()

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    FragmentDestinationScreen(
                        title = stringResource(R.string.destination_screen_title),
                        message = args.message,
                    ) {
                        // 결과를 설정합니다.
                        val result = Bundle().apply {
                            putParcelable(FRAGMENT_RESULT_KEY, Result(it))
                        }
                        setFragmentResult(Result.KEY, result)

                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    @Parcelize
    data class Result(
        val resultMessage: String = ""
    ): Parcelable {
        companion object {
            val KEY = this::class.qualifiedName!!
        }
    }
}
