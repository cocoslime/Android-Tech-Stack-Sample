package com.cocoslime.presentation.architecture.orbit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OrbitScreen(
    viewModel: OrbitViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect {sideEffect->
        when(sideEffect){
            is OrbitViewModel.SideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.title != null) {
            Text(
                text = uiState.title!!
            )
        }

        if (uiState.imageUrl != null) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberAsyncImagePainter(
                    model = uiState.imageUrl!!
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        TextButton(onClick = {
            viewModel.load()
        }) {
            Text("Reload Image")
        }
    }
}