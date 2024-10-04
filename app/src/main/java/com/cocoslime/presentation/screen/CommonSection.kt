package com.cocoslime.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonSection(
    title: String,
    message: String,
    isTextFieldVisible: Boolean,
    confirmButtonText: String,
    modifier: Modifier = Modifier,
    onConfirmClick: (String) -> Unit = {}
) {
    var state by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (isTextFieldVisible) {
            TextField(
                value = state,
                onValueChange = {
                    state = it
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Button(
            onClick = {
                onConfirmClick(state)
            }
        ) {
            Text(confirmButtonText)
        }
    }
}

@Composable
private fun getStatusBarHeight(): Dp {
    val windowInsets = WindowInsets.statusBars
    val density = LocalDensity.current
    return with(density) { windowInsets.getTop(this).toDp() }
}

@Preview
@Composable
private fun CommonScreenPreview() {
    MaterialTheme {
        CommonSection(
            title = "Title",
            message = "Message",
            confirmButtonText = "Confirm",
            isTextFieldVisible = true,
            onConfirmClick = {}
        )
    }
}