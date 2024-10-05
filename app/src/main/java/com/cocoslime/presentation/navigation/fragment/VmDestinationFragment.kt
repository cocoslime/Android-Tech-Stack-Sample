package com.cocoslime.presentation.navigation.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection
import com.cocoslime.presentation.common.FRAGMENT_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class VmDestinationFragment: Fragment() {

    private val viewModel: VmDestinationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    CommonSection(
                        title = getString(R.string.destination_screen_title),
                        message = viewModel.args.message,
                        isTextFieldVisible = true,
                        confirmButtonText = getString(R.string.prev_button_text),
                        modifier = Modifier.fillMaxSize(),
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