package com.cocoslime.presentation.navigation.fragment.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection
import com.cocoslime.presentation.navigation.fragment.dsl.FragmentNavRoute

@Composable
fun FragmentDestinationScreen(
    title: String,
    message: String,
    onConfirm: (String) -> Unit,
) {
    CommonSection(
        title = title,
        message = message,
        isTextFieldVisible = true,
        confirmButtonText = stringResource(R.string.prev_button_text),
        modifier = Modifier.fillMaxSize(),
        onConfirmClick = onConfirm
    )
}
