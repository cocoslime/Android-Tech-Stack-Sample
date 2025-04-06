package com.cocoslime.presentation.navigation.fragment.dsl

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

class VmDestinationFragment: Fragment() {

    private val viewModel: VmDestinationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // ViewModel 의 SavedStateHandle 로 가져오는 것과 동일하다.
                // 단, backStackEntry 의 SavedStateHandle 로는 가져올 수 없다.
                findNavController().currentBackStackEntry?.toRoute<FragmentNavRoute.VmDestinationArgs>()?.also {
                    println("VmDestinationFragment\ncurrentBackStackEntry.toRoute: $it")
                }
            }
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    CommonSection(
                        title = getString(R.string.vm_destination_screen_title),
                        message = viewModel.args.message,
                        isTextFieldVisible = true,
                        confirmButtonText = getString(R.string.prev_button_text),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        // 결과를 설정합니다. previousBackStackEntry
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            Result.KEY,
                            Result(it)
                        )

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
