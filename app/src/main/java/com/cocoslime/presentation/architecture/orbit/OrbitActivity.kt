package com.cocoslime.presentation.architecture.orbit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cocoslime.presentation.navigation.compose.ComposeMainNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectSideEffect

@AndroidEntryPoint
class OrbitActivity: ComponentActivity() {

    private val viewModel: OrbitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                OrbitScreen()
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.container.sideEffectFlow.collect { sideEffect->
                        when(sideEffect){
                            is OrbitViewModel.SideEffect.Toast -> {
                                Toast.makeText(
                                    this@OrbitActivity,
                                    sideEffect.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
        // viewModel.observe(state = ::render, sideEffect = ::handleSideEffect)
    }

    /*
    private fun render(state: UiState) {
        ...
    }

    private fun handleSideEffect(sideEffect: SideEffect) {
        when (sideEffect) {
            is Toast -> toast(sideEffect.text)
        }
    }
     */

}