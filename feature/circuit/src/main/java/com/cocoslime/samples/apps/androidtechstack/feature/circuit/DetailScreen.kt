package com.cocoslime.samples.apps.androidtechstack.feature.circuit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailScreen(val emailId: String) : Screen {
    data class State(
        val email: Email,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState
    sealed class Event : CircuitUiEvent {
        data object BackClicked : Event()
    }
}

class DetailPresenter(
    private val screen: DetailScreen,
    private val navigator: Navigator,
    private val emailRepository: EmailRepository
) : Presenter<DetailScreen.State> {
    @Composable
    override fun present(): DetailScreen.State {
        val email = EmailRepository.getEmail(screen.emailId)
        return DetailScreen.State(email) { event ->
            when (event) {
                is DetailScreen.Event.BackClicked -> {
                    navigator.pop()
                }
            }
        }
    }

    class Factory(private val emailRepository: EmailRepository) : Presenter.Factory {
        override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
            return when (screen) {
                is DetailScreen -> return DetailPresenter(screen, navigator, emailRepository)
                else -> null
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailDetail(
    state: DetailScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(state.email.subject) },
                navigationIcon = {
                    IconButton(onClick = { state.eventSink(DetailScreen.Event.BackClicked) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Text(text = state.email.subject)
        }
    }
}
