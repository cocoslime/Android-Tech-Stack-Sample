package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.fragment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.presentation.common.CommonSection

@Composable
fun SourceScreen(
    message: String,
    otherMessage: String? = null,
    navigateToDestination: (String) -> Unit,
    navigateToVmDestination: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()
        ) {
            CommonSection(
                title = stringResource(R.string.source_screen_title),
                message = message,
                isTextFieldVisible = true,
                confirmButtonText = stringResource(R.string.next_button_text),
                modifier = Modifier.weight(1f),
            ) { message ->
                navigateToDestination(message)
            }

            CommonSection(
                title = "",
                message = otherMessage ?: "",
                isTextFieldVisible = true,
                confirmButtonText = stringResource(R.string.vm_destination_screen_title),
                modifier = Modifier.weight(1f),
            ) { message ->
                navigateToVmDestination(message)
            }
        }
    }

}
