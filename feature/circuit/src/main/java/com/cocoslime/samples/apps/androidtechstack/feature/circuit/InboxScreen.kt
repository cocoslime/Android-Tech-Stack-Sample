package com.cocoslime.samples.apps.androidtechstack.feature.circuit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize


@Parcelize
data object InboxScreen : Screen {
    data class State(
        val emails: List<Email>,
        val eventSink: (Event) -> Unit
    ) : CircuitUiState
    sealed class Event : CircuitUiEvent {
        data object BackClicked : Event()
        data class EmailClicked(val emailId: String) : Event()
    }
}

data class Email(
    val emailId: String,
    val subject: String,
    val body: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inbox(
    state: InboxScreen.State,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Inbox") },
                navigationIcon = {
                    IconButton(onClick = { state.eventSink(InboxScreen.Event.BackClicked) }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(state.emails) { email ->
                EmailItem(
                    email = email,
                    onClick = {
                        state.eventSink(
                            InboxScreen.Event.EmailClicked(email.emailId)
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun EmailItem(
    email: Email,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .padding(8.dp)
            .heightIn(min = 40.dp)
            .clickable { onClick() }
    ) {
        Image(
            imageVector = Icons.Filled.Email,
            contentDescription = "Email Icon",
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Spacer(
            Modifier.width(8.dp)
        )

        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = email.subject,
                style = MaterialTheme.typography.titleSmall,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.fillMaxWidth()
            )
            Text(
                text = email.body,
                style = MaterialTheme.typography.bodySmall,
                modifier = modifier.fillMaxWidth()
            )
        }
    }

}

@Composable
@Preview
private fun EmailItemPreview() {
    Surface {
        EmailItem(
            email = Email(
                emailId = "1",
                subject = "Welcome to Circuit",
                body = "Circuit is a new way to build Android UIs with composable screens."
            ),
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

class InboxPresenter(
    private val navigator: Navigator,
    private val emailRepository: EmailRepository,
) : Presenter<InboxScreen.State> {
    @Composable
    override fun present(): InboxScreen.State {
        val emails by produceState<List<Email>>(initialValue = emptyList()) {
            value = EmailRepository.getEmails()
        }
        // Or a flow!
        // val emails by emailRepository.getEmailsFlow().collectAsState(initial = emptyList())
        return InboxScreen.State(emails) { event ->
            when (event) {
                is InboxScreen.Event.BackClicked -> {
                    navigator.pop()
                }
                is InboxScreen.Event.EmailClicked -> {
                    navigator.goTo(DetailScreen(emailId = event.emailId))
                }
            }
        }
    }
    class Factory(private val emailRepository: EmailRepository) : Presenter.Factory {
        override fun create(screen: Screen, navigator: Navigator, context: CircuitContext): Presenter<*>? {
            return when (screen) {
                InboxScreen -> return InboxPresenter(navigator, emailRepository)
                else -> null
            }
        }
    }
}
