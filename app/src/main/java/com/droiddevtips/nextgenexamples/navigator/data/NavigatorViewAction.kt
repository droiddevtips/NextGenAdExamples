package com.droiddevtips.nextgenexamples.navigator.data

sealed interface NavigatorViewAction {
    data class SetSelectedItem(val screen: Screen): NavigatorViewAction
}