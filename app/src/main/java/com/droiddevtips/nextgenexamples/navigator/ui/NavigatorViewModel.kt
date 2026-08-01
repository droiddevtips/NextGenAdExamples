package com.droiddevtips.nextgenexamples.navigator.ui

import androidx.lifecycle.ViewModel
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewAction
import com.droiddevtips.nextgenexamples.navigator.data.NavigatorViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigatorViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(NavigatorViewState())
    val viewState: StateFlow<NavigatorViewState>
        get() = _viewState.asStateFlow()

    fun performAction(action: NavigatorViewAction) {
        when(action) {
            is NavigatorViewAction.SetSelectedItem -> {
                _viewState.update { it.copy(selectedItem = action.screen) }
            }
        }
    }
}