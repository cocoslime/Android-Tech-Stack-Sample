package com.cocoslime.presentation.architecture.orbit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    viewModel.collectSideEffect { sideEffect->
        when(sideEffect){
            is OrbitViewModel.SideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            if (uiState.title != null) {
                Text(
                    text = uiState.title!!,
                    modifier = Modifier.padding(
                        horizontal = 16.dp, vertical = 8.dp
                    ),
                    style = MaterialTheme.typography.headlineSmall
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

                Button(onClick = {
                    viewModel.load()
                }) {
                    Text("Reload Image")
                }
            }
        }
    }
}