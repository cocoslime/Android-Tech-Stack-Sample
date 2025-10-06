package com.cocoslime.samples.apps.androidtechstack.presentation.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class FirstFragment : Fragment() {

    private val activityViewModel: ViewPagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = activityViewModel.text,
                            onValueChange = {
                                activityViewModel.text = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors().copy(
                                unfocusedContainerColor = Color.Blue.copy(
                                    alpha = 0.3f
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}
