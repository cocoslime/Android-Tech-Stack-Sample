package com.cocoslime.presentation.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cocoslime.presentation.R
import com.cocoslime.presentation.common.CommonSection

@Composable
fun ComposeMainNavHost() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        modifier = Modifier.fillMaxSize(),
        startDestination = ComposeMainRoute.Source
    ) {
        composable<ComposeMainRoute.Source> { navBackStackEntry ->
            val resultFromDestination by navBackStackEntry.savedStateHandle.getStateFlow(
                ComposeMainRoute.DestinationResult.KEY,
                ComposeMainRoute.DestinationResult.DEFAULT
            )
                .collectAsState(
                    initial = ComposeMainRoute.DestinationResult.DEFAULT
                )

            SourceScreen(
                resultFromDestination
            ) {
                controller.navigate(
                    route = ComposeMainRoute.DestinationArgs(it)
                )
            }
        }
        composable<ComposeMainRoute.DestinationArgs> { navBackStackEntry ->
            val destinationArgs = navBackStackEntry.toRoute<ComposeMainRoute.DestinationArgs>()

            DestinationScreen(
                destinationArgs,
                onConfirmClick = {
                    // 여기서는 previousBackStackEntry 를 사용해야 함.
                    controller.previousBackStackEntry?.savedStateHandle?.set(
                        ComposeMainRoute.DestinationResult.KEY,
                        ComposeMainRoute.DestinationResult(it)
                    )

                    controller.popBackStack()
                },
                onOtherButtonClick = {
                    controller.navigate(
                        route = ComposeMainRoute.OtherDestinationArgs("Other Destination")
                    ) {
                        controller.popBackStack()
                    }
                }
            )
        }
        composable<ComposeMainRoute.OtherDestinationArgs> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<ComposeMainRoute.OtherDestinationArgs>()

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                CommonSection(
                    title = "Other Destination",
                    message = args.message,
                    isTextFieldVisible = false,
                    confirmButtonText = stringResource(id = R.string.prev_button_text),
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    onConfirmClick = {
                        controller.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun SourceScreen(
    result: ComposeMainRoute.DestinationResult,
    onConfirmClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray
            )
    ) { paddingValues ->
        CommonSection(
            title = "Source",
            message = result.resultMessage,
            isTextFieldVisible = true,
            confirmButtonText = stringResource(id = R.string.next_button_text),
            modifier = Modifier
                .padding(paddingValues),
            onConfirmClick = onConfirmClick
        )
    }
}

@Composable
private fun DestinationScreen(
    args: ComposeMainRoute.DestinationArgs,
    onConfirmClick: (String) -> Unit,
    onOtherButtonClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFAAAA99)
            )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            CommonSection(
                title = "Destination",
                message = args.message,
                isTextFieldVisible = true,
                confirmButtonText = stringResource(id = R.string.prev_button_text),
                onConfirmClick = onConfirmClick
            )

            Button(onClick = onOtherButtonClick) {
                Text(text = "Pop & Move")
            }
        }
    }
}