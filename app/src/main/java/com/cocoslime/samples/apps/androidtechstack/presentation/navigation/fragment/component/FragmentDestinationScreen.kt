package com.cocoslime.samples.apps.androidtechstack.presentation.navigation.fragment.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.samples.apps.androidtechstack.R
import com.cocoslime.samples.apps.androidtechstack.presentation.common.CommonSection

@Composable
fun FragmentDestinationScreen(
    title: String,
    message: String,
    onConfirm: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        CommonSection(
            title = title,
            message = message,
            isTextFieldVisible = true,
            confirmButtonText = stringResource(R.string.prev_button_text),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            onConfirmClick = onConfirm
        )
    }
}
