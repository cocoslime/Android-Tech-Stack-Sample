package com.cocoslime.presentation.architecture.orbit

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.parcelize.Parcelize
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OrbitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dummyUseCase: DummyUseCase
): ViewModel(), ContainerHost<OrbitViewModel.UiState, OrbitViewModel.SideEffect> {

    override val container: Container<UiState, SideEffect> = container(
        initialState = UiState(),
        savedStateHandle = savedStateHandle,
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent { SideEffect.Toast(throwable.message.orEmpty()) }
            }
        }
    )

    init {
        load()
    }

    fun load() = intent {
        postSideEffect(SideEffect.Toast("Loading..."))
        reduce {
            state.copy(imageUrl = null)
        }

        if (state.title == null) {
            val title = dummyUseCase.getOrbitTitle()
            reduce {
                state.copy(
                    title = title
                )
            }
        }

        val imageUrl = dummyUseCase.getOrbitImageUrl()
        reduce {
            state.copy(
                imageUrl = imageUrl
            )
        }
    }

    @Parcelize
    data class UiState(
        val title: String? = null,
        val imageUrl: String? = null,
    ): Parcelable {
        companion object
    }

    sealed interface SideEffect {
        data class Toast(val message: String): SideEffect
    }
}
