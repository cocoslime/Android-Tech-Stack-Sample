package com.cocoslime.presentation.common

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun <T> StartActivityButton(
    activity: Activity,
    clazz: Class<T>,
    buttonText: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = {
            activity.startActivity(
                Intent(
                    activity,
                    clazz
                )
            )
        },
    ) {
        Text(
            buttonText,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
