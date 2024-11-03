package com.cocoslime.presentation.architecture.orbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.cocoslime.presentation.navigation.compose.ComposeMainNavHost

class OrbitActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                OrbitScreen()
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