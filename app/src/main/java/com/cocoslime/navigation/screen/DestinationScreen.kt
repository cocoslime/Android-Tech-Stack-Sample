package com.cocoslime.navigation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DestinationScreen(
    title: String,
    onConfirmClick: (String) -> Unit
) {
    var state by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = title)

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = state,
                onValueChange = {
                    state = it
                }
            )

            TextButton(
                onClick = {
                    onConfirmClick(state)
                }
            ) {
                Text("Confirm")
            }
        }
    }
}