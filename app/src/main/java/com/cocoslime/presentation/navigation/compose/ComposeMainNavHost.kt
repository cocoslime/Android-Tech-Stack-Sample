package com.cocoslime.presentation.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cocoslime.presentation.R
import com.cocoslime.presentation.screen.CommonScreen
import kotlinx.coroutines.flow.onEach

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
                .onEach {
                    println(it)
                }
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
                destinationArgs
            ) {
                // 여기서는 previousBackStackEntry 를 사용해야 함.
                controller.previousBackStackEntry?.savedStateHandle?.set(
                    ComposeMainRoute.DestinationResult.KEY,
                    ComposeMainRoute.DestinationResult(it)
                )

                controller.popBackStack()
            }
        }
    }
}

@Composable
private fun SourceScreen(
    result: ComposeMainRoute.DestinationResult,
    onConfirmClick: (String) -> Unit
) {
    CommonScreen(
        title = "Source",
        message = result.resultMessage,
        confirmButtonText = stringResource(id = R.string.next_button_text),
        onConfirmClick = onConfirmClick
    )
}

@Composable
private fun DestinationScreen(
    args: ComposeMainRoute.DestinationArgs,
    onConfirmClick: (String) -> Unit
) {
    CommonScreen(
        title = "Destination",
        message = args.message,
        confirmButtonText = stringResource(id = R.string.prev_button_text),
        onConfirmClick = onConfirmClick
    )
}