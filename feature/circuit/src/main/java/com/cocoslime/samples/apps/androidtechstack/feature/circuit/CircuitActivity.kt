package com.cocoslime.samples.apps.androidtechstack.feature.circuit

import android.os.Bundle
import androidx.activity.compose.setContent
import com.cocoslime.samples.apps.androidtechstack.common.base.BaseActivity
import com.cocoslime.samples.apps.androidtechstack.common.logDebug
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dagger.hilt.android.AndroidEntryPoint

/*
https://slackhq.github.io/circuit/tutorial/#__tabbed_1_1
 */
@AndroidEntryPoint
class CircuitActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val emailRepository = EmailRepository
        val circuit: Circuit =
            Circuit.Builder()
                .addPresenterFactory(InboxPresenter.Factory(emailRepository))
                .addUi<InboxScreen, InboxScreen.State> { state, modifier -> Inbox(state, modifier) }
                .addPresenterFactory(DetailPresenter.Factory(emailRepository))
                .addUi<DetailScreen, DetailScreen.State> { state, modifier -> EmailDetail(state, modifier) }
                .build()

        setContent {
            CircuitCompositionLocals(circuit) {
                val backStack = rememberSaveableBackStack(root = InboxScreen)
                val navigator = rememberCircuitNavigator(backStack) {
                    // Do something when the root screen is popped, usually exiting the app
                    "onRootPop".logDebug("CircuitActivity")
                    finish()
                }
                NavigableCircuitContent(navigator = navigator, backStack = backStack)
            }
        }
    }
}
