package com.cocoslime.samples.apps.androidtechstack.presentation.architecture.orbit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun OrbitScreen(
    viewModel: OrbitViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Button(
                onClick = {
                    viewModel.load()
                },
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Reload Image",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
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

            }
        }
    }
}
